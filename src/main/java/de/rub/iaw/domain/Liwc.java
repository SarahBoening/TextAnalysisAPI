package de.rub.iaw.domain;

/**
 * Entity class for the LIWC API
 * Saves the scores per catagory and the post ID of the analyzed text
 *
 * @author Sarah Böning
 **/

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "LIWC", catalog = "rotterdam")
public class Liwc implements java.io.Serializable {

	private static final long serialVersionUID = 7689487425948210500L;

	private Long id;

	// Foreign Key TCP_ID_FK for easy evaluation of data
	private TextCodeProb textCodeProb;

	// all 93 LIWC categories
	private Double achieve;
	private Double adj;
	private Double adverb;
	private Double affect;
	private Double affiliation;
	private Double allpunc;
	private Double analytic;
	private Double anger;
	private Double anx;
	private Double apostro;
	private Double article;
	private Double assent;
	private Double authentic;
	private Double auxverb;
	private Double bio;
	private Double body;
	private Double cause;
	private Double certain;
	private Double clout;
	private Double cogproc;
	private Double colon;
	private Double comma;
	private Double compare;
	private Double conj;
	private Double dash;
	private Double death;
	private Double dic;
	private Double differ;
	private Double discrep;
	private Double drives;
	private Double exclam;
	private Double family;
	private Double feel;
	private Double female;
	private Double filler;
	private Double focusfuture;
	private Double focuspast;
	private Double focuspresent;
	private Double friend;
	private Double function;
	private Double health;
	private Double hear;
	private Double home;
	private Double i;
	private Double informal;
	private Double ingest;
	private Double insight;
	private Double interrog;
	private Double ipron;
	private Double leisure;
	private Double male;
	private Double money;
	private Double motion;
	private Double negate;
	private Double negemo;
	private Double netspeak;
	private Double nonflu;
	private Double number;
	private Double otherp;
	private Double parenth;
	private Double percept;
	private Double period;
	private Double posemo;
	private Double power;
	private Double ppron;
	private Double prep;
	private Double pronoun;
	private Double qmark;
	private Double quant;
	private Double quote;
	private Double relativ;
	private Double relig;
	private Double reward;
	private Double risk;
	private Double sad;
	private Double see;
	private Double semic;
	private Double sexual;
	private Double shehe;
	private Double sixltr;
	private Double social;
	private Double space;
	private Double swear;
	private Double tentat;
	private Double they;
	private Double time;
	private Double tone;
	private Double verb;
	private Double wc;
	private Double we;
	private Double work;
	private Double wps;
	private Double you;

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Liwc() {

	}

	public Liwc(TextCodeProb textCodeProb) {
		this.textCodeProb = textCodeProb;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "LIWC_ID", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@OneToOne
	@JoinColumn(name = "TCP_ID_FK", referencedColumnName = "TEXTCODEPROB_ID", nullable = false)
	public TextCodeProb getTextCodeProb() {
		return textCodeProb;
	}

	public void setTextCodeProb(TextCodeProb textCodeProb) {
		this.textCodeProb = textCodeProb;
	}

	@Column(name = "ACHIEVE")
	public Double getAchieve() {
		return achieve;
	}

	public void setAchieve(Double achieve) {
		this.achieve = achieve;
	}

	@Column(name = "ADJ")
	public Double getAdj() {
		return adj;
	}

	public void setAdj(Double adj) {
		this.adj = adj;
	}

	@Column(name = "ADVERB")
	public Double getAdverb() {
		return adverb;
	}

	public void setAdverb(Double adverb) {
		this.adverb = adverb;
	}

	@Column(name = "AFFECT")
	public Double getAffect() {
		return affect;
	}

	public void setAffect(Double affect) {
		this.affect = affect;
	}

	@Column(name = "AFFILIATION")
	public Double getAffiliation() {
		return affiliation;
	}

	public void setAffiliation(Double affiliation) {
		this.affiliation = affiliation;
	}

	@Column(name = "ALLPUNC")
	public Double getAllpunc() {
		return allpunc;
	}

	public void setAllpunc(Double allpunc) {
		this.allpunc = allpunc;
	}

	@Column(name = "ANALYTIC")
	public Double getAnalytic() {
		return analytic;
	}

	public void setAnalytic(Double analytic) {
		this.analytic = analytic;
	}

	@Column(name = "ANGER")
	public Double getAnger() {
		return anger;
	}

	public void setAnger(Double anger) {
		this.anger = anger;
	}

	@Column(name = "ANX")
	public Double getAnx() {
		return anx;
	}

	public void setAnx(Double anx) {
		this.anx = anx;
	}

	@Column(name = "APOSTRO")
	public Double getApostro() {
		return apostro;
	}

	public void setApostro(Double apostro) {
		this.apostro = apostro;
	}

	@Column(name = "ARTICLE")
	public Double getArticle() {
		return article;
	}

	public void setArticle(Double article) {
		this.article = article;
	}

	@Column(name = "ASSENT")
	public Double getAssent() {
		return assent;
	}

	public void setAssent(Double assent) {
		this.assent = assent;
	}

	@Column(name = "AUTHENTIC")
	public Double getAuthentic() {
		return authentic;
	}

	public void setAuthentic(Double authentic) {
		this.authentic = authentic;
	}

	@Column(name = "AUXVERB")
	public Double getAuxverb() {
		return auxverb;
	}

	public void setAuxverb(Double auxverb) {
		this.auxverb = auxverb;
	}

	@Column(name = "BIO")
	public Double getBio() {
		return bio;
	}

	public void setBio(Double bio) {
		this.bio = bio;
	}

	@Column(name = "BODY")
	public Double getBody() {
		return body;
	}

	public void setBody(Double body) {
		this.body = body;
	}

	@Column(name = "CAUSE")
	public Double getCause() {
		return cause;
	}

	public void setCause(Double cause) {
		this.cause = cause;
	}

	@Column(name = "CERTAIN")
	public Double getCertain() {
		return certain;
	}

	public void setCertain(Double certain) {
		this.certain = certain;
	}

	@Column(name = "CLOUT")
	public Double getClout() {
		return clout;
	}

	public void setClout(Double clout) {
		this.clout = clout;
	}

	@Column(name = "COGPROC")
	public Double getCogproc() {
		return cogproc;
	}

	public void setCogproc(Double cogproc) {
		this.cogproc = cogproc;
	}

	@Column(name = "COLON")
	public Double getColon() {
		return colon;
	}

	public void setColon(Double colon) {
		this.colon = colon;
	}

	@Column(name = "COMMA")
	public Double getComma() {
		return comma;
	}

	public void setComma(Double comma) {
		this.comma = comma;
	}

	@Column(name = "COMPARE")
	public Double getCompare() {
		return compare;
	}

	public void setCompare(Double compare) {
		this.compare = compare;
	}

	@Column(name = "CONJ")
	public Double getConj() {
		return conj;
	}

	public void setConj(Double conj) {
		this.conj = conj;
	}

	@Column(name = "DASH")
	public Double getDash() {
		return dash;
	}

	public void setDash(Double dash) {
		this.dash = dash;
	}

	@Column(name = "DEATH")
	public Double getDeath() {
		return death;
	}

	public void setDeath(Double death) {
		this.death = death;
	}

	@Column(name = "DIC")
	public Double getDic() {
		return dic;
	}

	public void setDic(Double dic) {
		this.dic = dic;
	}

	@Column(name = "DIFFER")
	public Double getDiffer() {
		return differ;
	}

	public void setDiffer(Double differ) {
		this.differ = differ;
	}

	@Column(name = "DISCREP")
	public Double getDiscrep() {
		return discrep;
	}

	public void setDiscrep(Double discrep) {
		this.discrep = discrep;
	}

	@Column(name = "DRIVES")
	public Double getDrives() {
		return drives;
	}

	public void setDrives(Double drives) {
		this.drives = drives;
	}

	@Column(name = "EXCLAM")
	public Double getExclam() {
		return exclam;
	}

	public void setExclam(Double exclam) {
		this.exclam = exclam;
	}

	@Column(name = "FAMILY")
	public Double getFamily() {
		return family;
	}

	public void setFamily(Double family) {
		this.family = family;
	}

	@Column(name = "FEEL")
	public Double getFeel() {
		return feel;
	}

	public void setFeel(Double feel) {
		this.feel = feel;
	}

	@Column(name = "FEMALE")
	public Double getFemale() {
		return female;
	}

	public void setFemale(Double female) {
		this.female = female;
	}

	@Column(name = "FILLER")
	public Double getFiller() {
		return filler;
	}

	public void setFiller(Double filler) {
		this.filler = filler;
	}

	@Column(name = "FOCUSFUTURE")
	public Double getFocusfuture() {
		return focusfuture;
	}

	public void setFocusfuture(Double focusfuture) {
		this.focusfuture = focusfuture;
	}

	@Column(name = "FOCUSPAST")
	public Double getFocuspast() {
		return focuspast;
	}

	public void setFocuspast(Double focuspast) {
		this.focuspast = focuspast;
	}

	@Column(name = "FOCUSPRESENT")
	public Double getFocuspresent() {
		return focuspresent;
	}

	public void setFocuspresent(Double focuspresent) {
		this.focuspresent = focuspresent;
	}

	@Column(name = "FRIEND")
	public Double getFriend() {
		return friend;
	}

	public void setFriend(Double friend) {
		this.friend = friend;
	}

	@Column(name = "FUNCTION")
	public Double getFunction() {
		return function;
	}

	public void setFunction(Double function) {
		this.function = function;
	}

	@Column(name = "HEALTH")
	public Double getHealth() {
		return health;
	}

	public void setHealth(Double health) {
		this.health = health;
	}

	@Column(name = "HEAR")
	public Double getHear() {
		return hear;
	}

	public void setHear(Double hear) {
		this.hear = hear;
	}

	@Column(name = "HOME")
	public Double getHome() {
		return home;
	}

	public void setHome(Double home) {
		this.home = home;
	}

	@Column(name = "I")
	public Double getI() {
		return i;
	}

	public void setI(Double i) {
		this.i = i;
	}

	@Column(name = "INFORMAL")
	public Double getInformal() {
		return informal;
	}

	public void setInformal(Double informal) {
		this.informal = informal;
	}

	@Column(name = "INGEST")
	public Double getIngest() {
		return ingest;
	}

	public void setIngest(Double ingest) {
		this.ingest = ingest;
	}

	@Column(name = "INSIGHT")
	public Double getInsight() {
		return insight;
	}

	public void setInsight(Double insight) {
		this.insight = insight;
	}

	@Column(name = "INTERROG")
	public Double getInterrog() {
		return interrog;
	}

	public void setInterrog(Double interrog) {
		this.interrog = interrog;
	}

	@Column(name = "IPRON")
	public Double getIpron() {
		return ipron;
	}

	public void setIpron(Double ipron) {
		this.ipron = ipron;
	}

	@Column(name = "LEISURE")
	public Double getLeisure() {
		return leisure;
	}

	public void setLeisure(Double leisure) {
		this.leisure = leisure;
	}

	@Column(name = "MALE")
	public Double getMale() {
		return male;
	}

	public void setMale(Double male) {
		this.male = male;
	}

	@Column(name = "MONEY")
	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	@Column(name = "MOTION")
	public Double getMotion() {
		return motion;
	}

	public void setMotion(Double motion) {
		this.motion = motion;
	}

	@Column(name = "NEGATE")
	public Double getNegate() {
		return negate;
	}

	public void setNegate(Double negate) {
		this.negate = negate;
	}

	@Column(name = "NEGEMO")
	public Double getNegemo() {
		return negemo;
	}

	public void setNegemo(Double negemo) {
		this.negemo = negemo;
	}

	@Column(name = "NETSPEAK")
	public Double getNetspeak() {
		return netspeak;
	}

	public void setNetspeak(Double netspeak) {
		this.netspeak = netspeak;
	}

	@Column(name = "NONFLU")
	public Double getNonflu() {
		return nonflu;
	}

	public void setNonflu(Double nonflu) {
		this.nonflu = nonflu;
	}

	@Column(name = "NUMBER")
	public Double getNumber() {
		return number;
	}

	public void setNumber(Double number) {
		this.number = number;
	}

	@Column(name = "OTHERP")
	public Double getOtherp() {
		return otherp;
	}

	public void setOtherp(Double otherp) {
		this.otherp = otherp;
	}

	@Column(name = "PARENTH")
	public Double getParenth() {
		return parenth;
	}

	public void setParenth(Double parenth) {
		this.parenth = parenth;
	}

	@Column(name = "PERCEPT")
	public Double getPercept() {
		return percept;
	}

	public void setPercept(Double percept) {
		this.percept = percept;
	}

	@Column(name = "PERIOD")
	public Double getPeriod() {
		return period;
	}

	public void setPeriod(Double period) {
		this.period = period;
	}

	@Column(name = "POSEMO")
	public Double getPosemo() {
		return posemo;
	}

	public void setPosemo(Double posemo) {
		this.posemo = posemo;
	}

	@Column(name = "POWER")
	public Double getPower() {
		return power;
	}

	public void setPower(Double power) {
		this.power = power;
	}

	@Column(name = "PPRON")
	public Double getPpron() {
		return ppron;
	}

	public void setPpron(Double ppron) {
		this.ppron = ppron;
	}

	@Column(name = "PREP")
	public Double getPrep() {
		return prep;
	}

	public void setPrep(Double prep) {
		this.prep = prep;
	}

	@Column(name = "PRONOUN")
	public Double getPronoun() {
		return pronoun;
	}

	public void setPronoun(Double pronoun) {
		this.pronoun = pronoun;
	}

	@Column(name = "QMARK")
	public Double getQmark() {
		return qmark;
	}

	public void setQmark(Double qmark) {
		this.qmark = qmark;
	}

	@Column(name = "QUANT")
	public Double getQuant() {
		return quant;
	}

	public void setQuant(Double quant) {
		this.quant = quant;
	}

	@Column(name = "QUOTE")
	public Double getQuote() {
		return quote;
	}

	public void setQuote(Double quote) {
		this.quote = quote;
	}

	@Column(name = "RELATIV")
	public Double getRelativ() {
		return relativ;
	}

	public void setRelativ(Double relativ) {
		this.relativ = relativ;
	}

	@Column(name = "RELIG")
	public Double getRelig() {
		return relig;
	}

	public void setRelig(Double relig) {
		this.relig = relig;
	}

	@Column(name = "REWARD")
	public Double getReward() {
		return reward;
	}

	public void setReward(Double reward) {
		this.reward = reward;
	}

	@Column(name = "RISK")
	public Double getRisk() {
		return risk;
	}

	public void setRisk(Double risk) {
		this.risk = risk;
	}

	@Column(name = "SAD")
	public Double getSad() {
		return sad;
	}

	public void setSad(Double sad) {
		this.sad = sad;
	}

	@Column(name = "SEE")
	public Double getSee() {
		return see;
	}

	public void setSee(Double see) {
		this.see = see;
	}

	@Column(name = "SEMIC")
	public Double getSemic() {
		return semic;
	}

	public void setSemic(Double semic) {
		this.semic = semic;
	}

	@Column(name = "SEXUAL")
	public Double getSexual() {
		return sexual;
	}

	public void setSexual(Double sexual) {
		this.sexual = sexual;
	}

	@Column(name = "SHEHE")
	public Double getShehe() {
		return shehe;
	}

	public void setShehe(Double shehe) {
		this.shehe = shehe;
	}

	@Column(name = "SIXLTR")
	public Double getSixltr() {
		return sixltr;
	}

	public void setSixltr(Double sixltr) {
		this.sixltr = sixltr;
	}

	@Column(name = "SOCIAL")
	public Double getSocial() {
		return social;
	}

	public void setSocial(Double social) {
		this.social = social;
	}

	@Column(name = "SPACE")
	public Double getSpace() {
		return space;
	}

	public void setSpace(Double space) {
		this.space = space;
	}

	@Column(name = "SWEAR")
	public Double getSwear() {
		return swear;
	}

	public void setSwear(Double swear) {
		this.swear = swear;
	}

	@Column(name = "TENTAT")
	public Double getTentat() {
		return tentat;
	}

	public void setTentat(Double tentat) {
		this.tentat = tentat;
	}

	@Column(name = "THEY")
	public Double getThey() {
		return they;
	}

	public void setThey(Double they) {
		this.they = they;
	}

	@Column(name = "TIME")
	public Double getTime() {
		return time;
	}

	public void setTime(Double time) {
		this.time = time;
	}

	@Column(name = "TONE")
	public Double getTone() {
		return tone;
	}

	public void setTone(Double tone) {
		this.tone = tone;
	}

	@Column(name = "VERB")
	public Double getVerb() {
		return verb;
	}

	public void setVerb(Double verb) {
		this.verb = verb;
	}

	@Column(name = "WC")
	public Double getWc() {
		return wc;
	}

	public void setWc(Double wc) {
		this.wc = wc;
	}

	@Column(name = "WE")
	public Double getWe() {
		return we;
	}

	public void setWe(Double we) {
		this.we = we;
	}

	@Column(name = "WORK")
	public Double getWork() {
		return work;
	}

	public void setWork(Double work) {
		this.work = work;
	}

	@Column(name = "WPS")
	public Double getWps() {
		return wps;
	}

	public void setWps(Double wps) {
		this.wps = wps;
	}

	@Column(name = "YOU")
	public Double getYou() {
		return you;
	}

	public void setYou(Double you) {
		this.you = you;
	}

	@Override
	public String toString() {
		return "Liwc [id=" + id + ", textCodeProb=" + textCodeProb + ", achieve=" + achieve + ", adj=" + adj
				+ ", adverb=" + adverb + ", affect=" + affect + ", affiliation=" + affiliation + ", allpunc=" + allpunc
				+ ", analytic=" + analytic + ", anger=" + anger + ", anx=" + anx + ", apostro=" + apostro + ", article="
				+ article + ", assent=" + assent + ", authentic=" + authentic + ", auxverb=" + auxverb + ", bio=" + bio
				+ ", body=" + body + ", cause=" + cause + ", certain=" + certain + ", clout=" + clout + ", cogproc="
				+ cogproc + ", colon=" + colon + ", comma=" + comma + ", compare=" + compare + ", conj=" + conj
				+ ", dash=" + dash + ", death=" + death + ", dic=" + dic + ", differ=" + differ + ", discrep=" + discrep
				+ ", drives=" + drives + ", exclam=" + exclam + ", family=" + family + ", feel=" + feel + ", female="
				+ female + ", filler=" + filler + ", focusfuture=" + focusfuture + ", focuspast=" + focuspast
				+ ", focuspresent=" + focuspresent + ", friend=" + friend + ", function=" + function + ", health="
				+ health + ", hear=" + hear + ", home=" + home + ", i=" + i + ", informal=" + informal + ", ingest="
				+ ingest + ", insight=" + insight + ", interrog=" + interrog + ", ipron=" + ipron + ", leisure="
				+ leisure + ", male=" + male + ", money=" + money + ", motion=" + motion + ", negate=" + negate
				+ ", negemo=" + negemo + ", netspeak=" + netspeak + ", nonflu=" + nonflu + ", number=" + number
				+ ", otherp=" + otherp + ", parenth=" + parenth + ", percept=" + percept + ", period=" + period
				+ ", posemo=" + posemo + ", power=" + power + ", ppron=" + ppron + ", prep=" + prep + ", pronoun="
				+ pronoun + ", qmark=" + qmark + ", quant=" + quant + ", quote=" + quote + ", relativ=" + relativ
				+ ", relig=" + relig + ", reward=" + reward + ", risk=" + risk + ", sad=" + sad + ", see=" + see
				+ ", semic=" + semic + ", sexual=" + sexual + ", shehe=" + shehe + ", sixltr=" + sixltr + ", social="
				+ social + ", space=" + space + ", swear=" + swear + ", tentat=" + tentat + ", they=" + they + ", time="
				+ time + ", tone=" + tone + ", verb=" + verb + ", wc=" + wc + ", we=" + we + ", work=" + work + ", wps="
				+ wps + ", you=" + you + "]";
	}
}