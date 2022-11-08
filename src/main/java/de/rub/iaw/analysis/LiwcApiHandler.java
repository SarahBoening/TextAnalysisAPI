package de.rub.iaw.analysis;

/**
 * Handles the LIWC API by using HttpClient.
 * To call Receptiviti's LIWC API two API keys are necessary: X-API-KEY & X-API-SECRET-KEY. They can be generated when logged in at app.receptiviti.com
 * The LIWC Scores are saved to the database Liwc.
 *
 * @author Sarah Böning
 **/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.Random;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import de.rub.iaw.PropertiesConfiguration;
import de.rub.iaw.domain.Liwc;
import de.rub.iaw.exception.BadRequestException;
import de.rub.iaw.exception.LiwcApiException;
import de.rub.iaw.exception.UnauthorizedException;

public class LiwcApiHandler {

	private static Properties properties = PropertiesConfiguration.PropertyLoadHelper
			.loadProperties("application.properties");

	// For logging exceptions into log file
	private static Logger log = LogManager.getLogger(LiwcApiHandler.class.getName());

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// gets API response and formats it to String
	private static String getJSONResponse(CloseableHttpResponse response) {

		StringBuffer result = new StringBuffer();
		BufferedReader rd = null;
		String line = "";
		try {
			rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
		} catch (UnsupportedOperationException e) {
			log.error("ERR_WRITING_STRING_BUFFER_LIWC_API");
			throw new LiwcApiException("ERR_WRITING_STRING_BUFFER_LIWC_API");

		} catch (IOException e) {
			log.error("ERR_BUFFER_LIWC_API");
			throw new LiwcApiException("ERR_BUFFER_LIWC_API");
		} finally {
			try {
				rd.close();
				response.close();
			} catch (IOException e) {
				log.error("ERR_CLOSING_BUFFERED_READER_LIWC_API");
				throw new LiwcApiException("ERR_CLOSING_BUFFERED_READER_LIWC_API");
			}

		}
		return result.toString();
	}

	// actual API call
	private static String callAPI(Long id, String text) {

		// setting a timeout for the connection
		// http://www.baeldung.com/httpclient-timeout
		int timeout = 10;
		RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout * 1000)
				.setConnectionRequestTimeout(timeout * 1000).setSocketTimeout(timeout * 1000).build();
		CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();

		// How to POST with JSON body and header
		// http://www.baeldung.com/httpclient-post-http-request
		HttpPost post = new HttpPost(properties.getProperty("liwc.url"));
		String result = new String();
		StringEntity entity;
		JSONObject jsonBody = new JSONObject();
		jsonBody.put("name", properties.getProperty("liwc.name"));
		jsonBody.put("gender", properties.getProperty("liwc.gender"));
		// Unique Person ID
		jsonBody.put("person_id", id.toString() + Integer.toString(new Random().nextInt((1000 - 0 + 1) - 0)));

		JSONObject content = new JSONObject();
		content.put("language", properties.getProperty("liwc.language"));
		content.put("content_source", properties.getProperty("liwc.contentsource"));
		content.put("language_content", text);
		jsonBody.put("content", content);
		// person_handle needs to unique
		jsonBody.put("person_handle", id.toString() + Integer.toString(new Random().nextInt((1000 - 0 + 1) - 0)));
		String body = jsonBody.toString();
		try {
			entity = new StringEntity(body);
			post.setEntity(entity);
			post.setHeader("X-API-KEY", properties.getProperty("liwc.apikey"));
			post.setHeader("X-API-SECRET-KEY", properties.getProperty("liwc.apisecretkey"));
			post.setHeader("Accept", "application/json");
			post.setHeader("Content-type", "application/json");

			CloseableHttpResponse response = client.execute(post);

			// catching some errors that might occure
			if (response.getStatusLine().getStatusCode() == 400) {
				log.error("ERR_BAD_REQUEST_LIWC_API");
				throw new BadRequestException("ERR_BAD_REQUEST_LIWC_API");
			}
			if (response.getStatusLine().getStatusCode() == 401) {
				log.error("ERR_UNAUTH_REQUEST_LIWC_API");
				throw new UnauthorizedException("ERR_UNAUTH_REQUEST_LIWC_API");
			}
			if (response.getStatusLine().getStatusCode() == 200) {
				result = getJSONResponse(response);
			}
		} catch (ClientProtocolException e) {
			log.error("ERR_HTTP_PROTOCOLL_LIWC_API");
			throw new LiwcApiException("ERR_HTTP_PROTOCOLL_LIWC_API");
		} catch (java.net.SocketTimeoutException e) {
			log.error("ERR_LIWC_API_TIMEOUT");
			throw new LiwcApiException("ERR_LIWC_API_TIMEOUT");
		} catch (IOException e) {
			log.error("ERR_LIWC_API");
			throw new LiwcApiException("ERR_LIWC_API");
		} catch (UnsupportedOperationException e) {
			log.error("ERR_WRITING_STRING_BUFFER_LIWC_API");
			throw new LiwcApiException("ERR_WRITING_STRING_BUFFER_LIWC_API");
		} finally {
			try {
				client.close();
			} catch (IOException e) {
				log.error("ERR_CLOSING_HTTP_RESPONSE_LIWC_API");
				throw new LiwcApiException("ERR_CLOSING_HTTP_RESPONSE_LIWC_API");
			}
		}
		return result;
	}

	// saves the LIWC scores from the API response to the Liwc object
	private static Liwc saveScores(Liwc liwc, JSONObject json) {

		// extracts the important scores from the response JSON body
		JSONArray contents = (JSONArray) json.getJSONArray("contents");
		JSONObject temp = (JSONObject) contents.getJSONObject(0);
		JSONObject liwcScores = (JSONObject) temp.getJSONObject("liwc_scores");
		JSONObject liwcContents = (JSONObject) liwcScores.getJSONObject("categories");

		// getting the scores for the Liwc class and updates liwc object
		// *100 to get the percentage
		double tone = liwcScores.getDouble("tone");
		liwc.setTone(tone);
		double sixLtr = liwcScores.getDouble("sixLtr");
		liwc.setSixltr(sixLtr);
		double clout = liwcScores.getDouble("clout");
		liwc.setClout(clout);
		double wps = liwcScores.getDouble("wps");
		liwc.setWps(wps);
		double analytic = liwcScores.getDouble("analytic");
		liwc.setAnalytic(analytic);
		double wc = liwcScores.getDouble("wc");
		liwc.setWc(wc);
		double dic = liwcScores.getDouble("dic");
		liwc.setDic(dic);
		double authentic = liwcScores.getDouble("authentic");
		liwc.setAuthentic(authentic);
		double family = liwcContents.getDouble("family") * 100;
		liwc.setFamily(family);
		double feel = liwcContents.getDouble("feel") * 100;
		liwc.setFeel(feel);
		double money = liwcContents.getDouble("money") * 100;
		liwc.setMoney(money);
		double insight = liwcContents.getDouble("insight") * 100;
		liwc.setInsight(insight);
		double number = liwcContents.getDouble("number") * 100;
		liwc.setNumber(number);
		double parenth = liwcContents.getDouble("Parenth") * 100;
		liwc.setParenth(parenth);
		double cogproc = liwcContents.getDouble("cogproc") * 100;
		liwc.setCogproc(cogproc);
		double otherP = liwcContents.getDouble("OtherP") * 100;
		liwc.setOtherp(otherP);
		double female = liwcContents.getDouble("female") * 100;
		liwc.setFemale(female);
		double negate = liwcContents.getDouble("negate") * 100;
		liwc.setNegate(negate);
		double negemo = liwcContents.getDouble("negemo") * 100;
		liwc.setNegemo(negemo);
		double differ = liwcContents.getDouble("differ") * 100;
		liwc.setDiffer(differ);
		double death = liwcContents.getDouble("death") * 100;
		liwc.setDeath(death);
		double adverb = liwcContents.getDouble("adverb") * 100;
		liwc.setAdverb(adverb);
		double informal = liwcContents.getDouble("informal") * 100;
		liwc.setInformal(informal);
		double ipron = liwcContents.getDouble("ipron") * 100;
		liwc.setIpron(ipron);
		double percept = liwcContents.getDouble("percept") * 100;
		liwc.setPercept(percept);
		double quant = liwcContents.getDouble("quant") * 100;
		liwc.setQuant(quant);
		double exclam = liwcContents.getDouble("Exclam") * 100;
		liwc.setExclam(exclam);
		double adj = liwcContents.getDouble("adj") * 100;
		liwc.setAdj(adj);
		double prep = liwcContents.getDouble("prep") * 100;
		liwc.setPrep(prep);
		double achieve = liwcContents.getDouble("achieve") * 100;
		liwc.setAchieve(achieve);
		double function = liwcContents.getDouble("function") * 100;
		liwc.setFunction(function);
		double bio = liwcContents.getDouble("bio") * 100;
		liwc.setBio(bio);
		double risk = liwcContents.getDouble("risk") * 100;
		liwc.setRisk(risk);
		double leisure = liwcContents.getDouble("leisure") * 100;
		liwc.setLeisure(leisure);
		double quote = liwcContents.getDouble("Quote") * 100;
		liwc.setQuote(quote);
		double verb = liwcContents.getDouble("verb") * 100;
		liwc.setVerb(verb);
		double hear = liwcContents.getDouble("hear") * 100;
		liwc.setHear(hear);
		double they = liwcContents.getDouble("they") * 100;
		liwc.setThey(they);
		double affect = liwcContents.getDouble("affect") * 100;
		liwc.setAffect(affect);
		double you = liwcContents.getDouble("you") * 100;
		liwc.setYou(you);
		double work = liwcContents.getDouble("work") * 100;
		liwc.setWork(work);
		double period = liwcContents.getDouble("Period") * 100;
		liwc.setPeriod(period);
		double friend = liwcContents.getDouble("friend") * 100;
		liwc.setFriend(friend);
		double focusfuture = liwcContents.getDouble("focusfuture") * 100;
		liwc.setFocusfuture(focusfuture);
		double auxverb = liwcContents.getDouble("auxverb") * 100;
		liwc.setAuxverb(auxverb);
		double male = liwcContents.getDouble("male") * 100;
		liwc.setMale(male);
		double shehe = liwcContents.getDouble("shehe") * 100;
		liwc.setShehe(shehe);
		double semiC = liwcContents.getDouble("SemiC") * 100;
		liwc.setSemic(semiC);
		double relig = liwcContents.getDouble("relig") * 100;
		liwc.setRelig(relig);
		double compare = liwcContents.getDouble("compare") * 100;
		liwc.setCompare(compare);
		double pronoun = liwcContents.getDouble("pronoun") * 100;
		liwc.setPronoun(pronoun);
		double qMark = liwcContents.getDouble("QMark") * 100;
		liwc.setQmark(qMark);
		double certain = liwcContents.getDouble("certain") * 100;
		liwc.setCertain(certain);
		double assent = liwcContents.getDouble("assent") * 100;
		liwc.setAssent(assent);
		double we = liwcContents.getDouble("we") * 100;
		liwc.setWe(we);
		double sad = liwcContents.getDouble("sad");
		liwc.setSad(sad);
		double affiliation = liwcContents.getDouble("affiliation") * 100;
		liwc.setAffiliation(affiliation);
		double see = liwcContents.getDouble("see") * 100;
		liwc.setSee(see);
		double anger = liwcContents.getDouble("anger") * 100;
		liwc.setAnger(anger);
		double home = liwcContents.getDouble("home") * 100;
		liwc.setHome(home);
		double conj = liwcContents.getDouble("conj") * 100;
		liwc.setConj(conj);
		double sexual = liwcContents.getDouble("sexual") * 100;
		liwc.setSexual(sexual);
		double ppron = liwcContents.getDouble("ppron") * 100;
		liwc.setPpron(ppron);
		double dash = liwcContents.getDouble("Dash") * 100;
		liwc.setDash(dash);
		double space = liwcContents.getDouble("space") * 100;
		liwc.setSpace(space);
		double filler = liwcContents.getDouble("filler") * 100;
		liwc.setFiller(filler);
		double anx = liwcContents.getDouble("anx") * 100;
		liwc.setAnx(anx);
		double focuspresent = liwcContents.getDouble("focuspresent") * 100;
		liwc.setFocuspresent(focuspresent);
		double netspeak = liwcContents.getDouble("netspeak") * 100;
		liwc.setNetspeak(netspeak);
		double health = liwcContents.getDouble("female") * 100;
		liwc.setHealth(health);
		double discrep = liwcContents.getDouble("discrep") * 100;
		liwc.setDiscrep(discrep);
		double relativ = liwcContents.getDouble("relativ") * 100;
		liwc.setRelativ(relativ);
		double colon = liwcContents.getDouble("Colon") * 100;
		liwc.setColon(colon);
		double nonflu = liwcContents.getDouble("nonflu") * 100;
		liwc.setNonflu(nonflu);
		double cause = liwcContents.getDouble("cause") * 100;
		liwc.setCause(cause);
		double body = liwcContents.getDouble("body") * 100;
		liwc.setBody(body);
		double tentat = liwcContents.getDouble("tentat") * 100;
		liwc.setTentat(tentat);
		double power = liwcContents.getDouble("power") * 100;
		liwc.setPower(power);
		double interrog = liwcContents.getDouble("interrog") * 100;
		liwc.setInterrog(interrog);
		double social = liwcContents.getDouble("social") * 100;
		liwc.setSocial(social);
		double drives = liwcContents.getDouble("drives") * 100;
		liwc.setDrives(drives);
		double focuspast = liwcContents.getDouble("focuspast") * 100;
		liwc.setFocuspast(focuspast);
		double article = liwcContents.getDouble("article") * 100;
		liwc.setArticle(article);
		double allPunc = liwcContents.getDouble("AllPunc") * 100;
		liwc.setAllpunc(allPunc);
		double apostro = liwcContents.getDouble("Apostro") * 100;
		liwc.setApostro(apostro);
		double i = liwcContents.getDouble("i") * 100;
		liwc.setI(i);
		double posemo = liwcContents.getDouble("posemo") * 100;
		liwc.setPosemo(posemo);
		double ingest = liwcContents.getDouble("ingest") * 100;
		liwc.setIngest(ingest);
		double motion = liwcContents.getDouble("motion") * 100;
		liwc.setMotion(motion);
		double swear = liwcContents.getDouble("swear") * 100;
		liwc.setSwear(swear);
		double comma = liwcContents.getDouble("Comma") * 100;
		liwc.setComma(comma);
		double time = liwcContents.getDouble("time") * 100;
		liwc.setTime(time);
		double reward = liwcContents.getDouble("reward") * 100;
		liwc.setReward(reward);
		return liwc;
	}

	// Method that gets called from the Controller class
	public static Liwc getLiwcScoresFromApi(Liwc liwc, Long id, String text) {

		return saveScores(liwc, new JSONObject(callAPI(id, text)));

	}

}
