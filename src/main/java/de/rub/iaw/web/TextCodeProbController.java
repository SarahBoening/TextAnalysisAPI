package de.rub.iaw.web;

/**
 * Handles the API calls for the Text Code Probability calculations
 * 
 * @author Sarah Böning
 **/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.EnumUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.rub.iaw.domain.Empath;
import de.rub.iaw.domain.Liwc;
import de.rub.iaw.domain.Logevent;
import de.rub.iaw.domain.TextCodeProb;
import de.rub.iaw.exception.BadParameterException;
import de.rub.iaw.exception.TextCodeProbControllerException;
import de.rub.iaw.service.db.EmpathService;
import de.rub.iaw.service.db.LiwcService;
import de.rub.iaw.service.db.LogeventService;
import de.rub.iaw.service.db.TextCodeProbService;
import de.rub.iaw.util.PromptingEnum_LanguageCodes;
import de.rub.iaw.util.TextEnum_EmpathCategories;
import de.rub.iaw.util.TextEnum_LiwcCategories;
import de.rub.iaw.util.TextEnum_Program;
import de.rub.iaw.PropertiesConfiguration;
import de.rub.iaw.analysis.CodeProbCalculator;
import de.rub.iaw.analysis.EmpathApiHandler;
import de.rub.iaw.analysis.LiwcApiHandler;

@RestController
@RequestMapping(value = "/text")
public class TextCodeProbController {

	@Autowired
	TextCodeProbService textCodeProbService;

	@Autowired
	LiwcService liwcService;

	@Autowired
	EmpathService empathService;

	@Autowired
	LogeventService logeventService;

	private static Logger log = LogManager.getLogger(LiwcApiHandler.class.getName());

	private static String[] allCodes = { "0", "1", "2a", "3", "4", "5", "6a", "6b", "7", "84", "85", "9a", "9b", "10",
			"A1", "A2", "A3" };

	private static Properties properties = PropertiesConfiguration.PropertyLoadHelper
			.loadProperties("application.properties");

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@RequestMapping(value = "program/{program}/code/{code}", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public Map<String, Object> getSpecificCodeProb(@PathVariable String code, @PathVariable String program,
			@RequestBody TextCodeProb param, @RequestParam(value = "lang", defaultValue = "en") String language) {

		logeventService.create(new Logevent(
				"TEXT_CODE_PROB_FOR_CODES" + code.toUpperCase() + "_REQUESTED_WITH_PROGRAM: " + program.toUpperCase()));

		// validating input
		checkLanguage(language);
		checkProgram(program);
		checkCode(code);

		TextCodeProb textCodeProb = callApisCalculateProbs(param);

		// this makes sure only the requested entries are returned
		// TreeMap: response is sorted => easier to read
		Map<String, Object> response = new TreeMap<String, Object>();
		response.put("TextCodeProb_Id", textCodeProb.getId());
		if (textCodeProb.getPostID() != 0)
			response.put("postId", textCodeProb.getPostID().toString());
		if (textCodeProb.getPostDate() != null)
			response.put("postDate", textCodeProb.getPostDate().toString());
		if (program.equals("liwc")) {
			try {
				Object value = PropertyUtils.getProperty(textCodeProb, "liwc_" + code);
				response.put("liwc_" + code, value.toString());
			} catch (IllegalAccessException e) {
				log.error("ERR_TEXTCODEPROB_CONTROLLER_ILLEGAL_ACCESS_EXCEPTION");
				throw new TextCodeProbControllerException("ERR_TEXTCODEPROB_CONTROLLER_ILLEGAL_ACCESS_EXCEPTION");
			} catch (InvocationTargetException e) {
				log.error("ERR_TEXTCODEPROB_CONTROLLER_INVOCATION_TARGET_EXCEPTION");
				throw new TextCodeProbControllerException("ERR_TEXTCODEPROB_CONTROLLER_INVOCATION_TARGET_EXCEPTION");
			} catch (NoSuchMethodException e) {
				log.error("ERR_TEXTCODEPROB_CONTROLLER_NO_SUCH_METHOD_EXCEPTION");
				throw new TextCodeProbControllerException("ERR_TEXTCODEPROB_CONTROLLER_NO_SUCH_METHOD_EXCEPTION");
			}
		} else if (program.equals("empath")) {
			try {
				Object value = PropertyUtils.getProperty(textCodeProb, "empath_" + code);
				response.put("empath_" + code, value.toString());
			} catch (IllegalAccessException e) {
				log.error("ERR_TEXTCODEPROB_CONTROLLER_ILLEGAL_ACCESS_EXCEPTION");
				throw new TextCodeProbControllerException("ERR_TEXTCODEPROB_CONTROLLER_ILLEGAL_ACCESS_EXCEPTION");
			} catch (InvocationTargetException e) {
				log.error("ERR_TEXTCODEPROB_CONTROLLER_INVOCATION_TARGET_EXCEPTION");
				throw new TextCodeProbControllerException("ERR_TEXTCODEPROB_CONTROLLER_INVOCATION_TARGET_EXCEPTION");
			} catch (NoSuchMethodException e) {
				log.error("ERR_TEXTCODEPROB_CONTROLLER_NO_SUCH_METHOD_EXCEPTION");
				throw new TextCodeProbControllerException("ERR_TEXTCODEPROB_CONTROLLER_NO_SUCH_METHOD_EXCEPTION");
			}
		}

		logeventService.create(new Logevent("SUCCESSFUL_TEXT_CODE_PROB_FOR_CODES" + code.toUpperCase()
				+ "_REQUESTED_WITH_PROGRAM: " + program.toUpperCase()));

		return response;
	}

	// Calculates the probability for all codes
	@RequestMapping(value = "program/{program}/code", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getAllCodeProbs(@PathVariable String program, @RequestBody TextCodeProb param,
			@RequestParam(value = "lang", defaultValue = "en") String language) {

		logeventService
				.create(new Logevent("TEXT_CODE_PROB_FOR_ALL_CODES_REQUESTED_WITH_PROGRAM: " + program.toUpperCase()));

		// validating input
		checkLanguage(language);
		checkProgram(program);

		TextCodeProb textCodeProb = callApisCalculateProbs(param);

		// this makes sure only the requested entries are returned
		// TreeMap: response is sorted => easier to read
		Map<String, Object> response = new TreeMap<String, Object>();
		response.put("TextCodeProb_Id", textCodeProb.getId());
		if (textCodeProb.getPostID() != null)
			response.put("postId", textCodeProb.getPostID().toString());
		if (textCodeProb.getPostDate() != null)
			response.put("postDate", textCodeProb.getPostDate().toString());
		if (program.equals("liwc")) {
			for (String code : allCodes) {
				try {
					Object value = PropertyUtils.getProperty(textCodeProb, "liwc_" + code);
					response.put("liwc_" + code, value.toString());
				} catch (IllegalAccessException e) {
					log.error("ERR_TEXTCODEPROB_CONTROLLER_ILLEGAL_ACCESS_EXCEPTION");
					throw new TextCodeProbControllerException("ERR_TEXTCODEPROB_CONTROLLER_ILLEGAL_ACCESS_EXCEPTION");
				} catch (InvocationTargetException e) {
					log.error("ERR_TEXTCODEPROB_CONTROLLER_INVOCATION_TARGET_EXCEPTION");
					throw new TextCodeProbControllerException(
							"ERR_TEXTCODEPROB_CONTROLLER_INVOCATION_TARGET_EXCEPTION");
				} catch (NoSuchMethodException e) {
					log.error("ERR_TEXTCODEPROB_CONTROLLER_NO_SUCH_METHOD_EXCEPTION");
					throw new TextCodeProbControllerException("ERR_TEXTCODEPROB_CONTROLLER_NO_SUCH_METHOD_EXCEPTION");
				}
			}
		} else if (program.equals("empath")) {
			for (String code : allCodes) {
				try {
					Object value = PropertyUtils.getProperty(textCodeProb, "empath_" + code);
					response.put("empath_" + code, value.toString());
				} catch (IllegalAccessException e) {
					log.error("ERR_TEXTCODEPROB_CONTROLLER_ILLEGAL_ACCESS_EXCEPTION");
					throw new TextCodeProbControllerException("ERR_TEXTCODEPROB_CONTROLLER_ILLEGAL_ACCESS_EXCEPTION");
				} catch (InvocationTargetException e) {
					log.error("ERR_TEXTCODEPROB_CONTROLLER_INVOCATION_TARGET_EXCEPTION");
					throw new TextCodeProbControllerException(
							"ERR_TEXTCODEPROB_CONTROLLER_INVOCATION_TARGET_EXCEPTION");
				} catch (NoSuchMethodException e) {
					log.error("ERR_TEXTCODEPROB_CONTROLLER_NO_SUCH_METHOD_EXCEPTION");
					throw new TextCodeProbControllerException("ERR_TEXTCODEPROB_CONTROLLER_NO_SUCH_METHOD_EXCEPTION");
				}
			}
		}

		logeventService.create(new Logevent(
				"SUCCESSFUL_TEXT_CODE_PROB_FOR_ALL_CODES_REQUESTED_WITH_PROGRAM: " + program.toUpperCase()));

		return response;
	}

	@RequestMapping(value = "program/{program}/code/selected/{codes}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getSelectedCodeProbs(@PathVariable String program, @PathVariable String[] codes,
			@RequestBody TextCodeProb param, @RequestParam(value = "lang", defaultValue = "en") String language) {

		logeventService.create(new Logevent("TEXT_CODE_PROB_FOR_SELECTED_CODES" + codes.toString().toUpperCase()
				+ "_REQUESTED_WITH_PROGRAM: " + program.toUpperCase()));

		// validating input
		checkLanguage(language);
		checkProgram(program);
		for (String code : codes)
			checkCode(code);

		TextCodeProb textCodeProb = callApisCalculateProbs(param);

		// this makes sure only the requested entries are returned
		// TreeMap: response is sorted => easier to read
		Map<String, Object> response = new TreeMap<String, Object>();
		response.put("TextCodeProb_Id", textCodeProb.getId());
		if (textCodeProb.getPostID() != 0)
			response.put("postId", textCodeProb.getPostID().toString());
		if (textCodeProb.getPostDate() != null)
			response.put("postDate", textCodeProb.getPostDate().toString());
		if (program.equals("liwc")) {
			for (String code : codes) {
				try {
					Object value = PropertyUtils.getProperty(textCodeProb, "liwc_" + code);
					response.put("liwc_" + code, value.toString());
				} catch (IllegalAccessException e) {
					log.error("ERR_TEXTCODEPROB_CONTROLLER_ILLEGAL_ACCESS_EXCEPTION");
					throw new TextCodeProbControllerException("ERR_TEXTCODEPROB_CONTROLLER_ILLEGAL_ACCESS_EXCEPTION");
				} catch (InvocationTargetException e) {
					log.error("ERR_TEXTCODEPROB_CONTROLLER_INVOCATION_TARGET_EXCEPTION");
					throw new TextCodeProbControllerException(
							"ERR_TEXTCODEPROB_CONTROLLER_INVOCATION_TARGET_EXCEPTION");
				} catch (NoSuchMethodException e) {
					log.error("ERR_TEXTCODEPROB_CONTROLLER_NO_SUCH_METHOD_EXCEPTION");
					throw new TextCodeProbControllerException("ERR_TEXTCODEPROB_CONTROLLER_NO_SUCH_METHOD_EXCEPTION");
				}
			}
		} else if (program.equals("empath")) {
			for (String code : codes) {
				try {
					Object value = PropertyUtils.getProperty(textCodeProb, "empath_" + code);
					response.put("empath_" + code, value.toString());
				} catch (IllegalAccessException e) {
					log.error("ERR_TEXTCODEPROB_CONTROLLER_ILLEGAL_ACCESS_EXCEPTION");
					throw new TextCodeProbControllerException("ERR_TEXTCODEPROB_CONTROLLER_ILLEGAL_ACCESS_EXCEPTION");
				} catch (InvocationTargetException e) {
					log.error("ERR_TEXTCODEPROB_CONTROLLER_INVOCATION_TARGET_EXCEPTION");
					throw new TextCodeProbControllerException(
							"ERR_TEXTCODEPROB_CONTROLLER_INVOCATION_TARGET_EXCEPTION");
				} catch (NoSuchMethodException e) {
					log.error("ERR_TEXTCODEPROB_CONTROLLER_NO_SUCH_METHOD_EXCEPTION");
					throw new TextCodeProbControllerException("ERR_TEXTCODEPROB_CONTROLLER_NO_SUCH_METHOD_EXCEPTION");
				}
			}
		}

		logeventService.create(new Logevent("SUCCESSFUL_TEXT_CODE_PROB_FOR_SELECTED_CODES"
				+ codes.toString().toUpperCase() + "_REQUESTED_WITH_PROGRAM: " + program.toUpperCase()));

		return response;
	}

	@RequestMapping(value = "program/{program}/isCodeInText/{code}", method = RequestMethod.POST)
	public boolean isCodeInText(@PathVariable String program, @PathVariable String code,
			@RequestBody TextCodeProb param, @RequestParam(value = "lang", defaultValue = "en") String language) {

		logeventService.create(new Logevent("TEXT_CODE_PROB_BOOLEAN_REQUESTED_WITH_PROGRAM: " + program.toUpperCase()));

		// validate input
		checkLanguage(language);
		checkProgram(program);
		checkCode(code);

		TextCodeProb textCodeProb = callApisCalculateProbs(param);

		boolean isInText = false;
		if (program.equals("liwc"))
			isInText = isCodeInTextLiwc(textCodeProb, code);
		if (program.equals("empath"))
			isInText = isCodeInTextEmpath(textCodeProb, code);

		logeventService.create(
				new Logevent("SUCCESSFUL_TEXT_CODE_PROB_BOOLEAN_REQUESTED_WITH_PROGRAM: " + program.toUpperCase()));

		return isInText;

	}

	@RequestMapping(value = "program/{program}/categories/{category}", method = RequestMethod.POST)
	@ResponseBody
	public double getSpecificCategoryValue(@PathVariable String program, @PathVariable String category,
			@RequestBody TextCodeProb param, @RequestParam(value = "lang", defaultValue = "en") String language) {

		logeventService
				.create(new Logevent(program.toUpperCase() + "_CATEGORY_" + category.toUpperCase() + "_REQUEST"));

		// validate input
		checkLanguage(language);
		checkProgram(program);
		checkCategory(program, category);

		TextCodeProb textCodeProb = callApisCalculateProbs(param);

		logeventService.create(new Logevent(
				"SUCCESSFUL_" + program.toUpperCase() + "_CATEGORY_" + category.toUpperCase() + "_REQUEST"));

		return getCategoryValue(textCodeProb, program, category);
	}

	@RequestMapping(value = "id/{id}", method = RequestMethod.GET)
	@ResponseBody
	public TextCodeProb getCodeProbByID(@PathVariable Long id) {

		Logevent logevent = new Logevent("TEXTCODEPROB_REQUESTED_BY_ID");
		logevent.setTextCodeProbId(textCodeProbService.findById(id).getId());
		logeventService.create(logevent);

		TextCodeProb textCodeProb = textCodeProbService.findById(id);

		if (textCodeProb == null) {
			log.error("ERR_BAD_PARAM_ID_INVALID");
			throw new BadParameterException("ERR_BAD_PARAM_ID_INVALID");
		}

		
		Logevent logeventSuccessful = new Logevent("SUCCESSFUL_TEXTCODEPROB_REQUESTED_BY_ID");
		logeventSuccessful.setTextCodeProbId(textCodeProbService.findById(id).getId());
		logeventService.create(logeventSuccessful);

		return textCodeProb;
	}

	@RequestMapping(value = "postId/{postId}", method = RequestMethod.GET)
	@ResponseBody
	public TextCodeProb getCodeProbByPostID(@PathVariable Long postId) {

		Logevent logevent = new Logevent("TEXTCODEPROB_REQUESTED_BY_POST_ID");
		logevent.setTextCodeProbId(textCodeProbService.findByPostID(postId).getPostID());
		logeventService.create(logevent);

		TextCodeProb textCodeProb = textCodeProbService.findByPostID(postId);
		
		if (textCodeProb == null) {
			log.error("ERR_BAD_PARAM_POSTID_INVALID");
			throw new BadParameterException("ERR_BAD_PARAM_POSTID_INVALID");
		}

		
		Logevent logeventSuccessful = new Logevent("SUCCESSFUL_TEXTCODEPROB_REQUESTED_BY_POST_ID");
		logeventSuccessful.setTextCodeProbId(textCodeProbService.findByPostID(postId).getPostID());
		logeventService.create(logeventSuccessful);

		return textCodeProb;
	}

	@RequestMapping(value = "/program/{program}/isCodeInText/code/{code}/postId/{postId}", method = RequestMethod.GET)
	@ResponseBody
	public boolean getIsCodeInTextByPostId(@PathVariable String program, @PathVariable String code,
			@PathVariable Long postId) {

		Logevent logevent = new Logevent("TEXTCODEPROB_BOOLEAN_REQUESTED_BY_POST_ID_WITH_PROGRAM: "
				+ program.toUpperCase() + "FOR_CODE " + code.toUpperCase());
		logevent.setTextCodeProbId(postId);
		logeventService.create(logevent);

		// validate input
		checkProgram(program);
		checkCode(code);

		boolean isInText = false;
		TextCodeProb textCodeProb = textCodeProbService.findById(postId);
		
		if (textCodeProb == null) {
			log.error("ERR_BAD_PARAM_POSTID_INVALID");
			throw new BadParameterException("ERR_BAD_PARAM_POSTID_INVALID");
		}
		
		if (program.equals("liwc"))
			isInText = isCodeInTextLiwc(textCodeProb, code);
		if (program.equals("empath"))
			isInText = isCodeInTextEmpath(textCodeProb, code);

		Logevent logeventSuccessful = new Logevent("SUCCESSFUL_TEXTCODEPROB_BOOLEAN_REQUESTED_BY_POST_ID_WITH_PROGRAM: "
				+ program.toUpperCase() + "FOR_CODE: " + code.toUpperCase());
		logeventSuccessful.setTextCodeProbId(postId);
		logeventService.create(logeventSuccessful);

		return isInText;
	}

	@RequestMapping(value = "/program/{program}/isCodeInText/code/{code}/id/{id}", method = RequestMethod.GET)
	@ResponseBody
	public boolean getIsCodeInTextById(@PathVariable String program, @PathVariable String code, @PathVariable Long id) {

		Logevent logevent = new Logevent("TEXTCODEPROB_BOOLEAN_REQUESTED_BY_ID_WITH_PROGRAM: " + program.toUpperCase()
				+ "FOR_CODE: " + code.toUpperCase());
		logevent.setTextCodeProbId(id);
		logeventService.create(logevent);

		// validate input
		checkProgram(program);
		checkCode(code);

		boolean isInText = false;
		TextCodeProb textCodeProb = textCodeProbService.findById(id);
		
		if (textCodeProb == null) {
			log.error("ERR_BAD_PARAM_ID_INVALID");
			throw new BadParameterException("ERR_BAD_PARAM_ID_INVALID");
		}
		
		if (program.equals("liwc"))
			isInText = isCodeInTextLiwc(textCodeProb, code);
		if (program.equals("empath"))
			isInText = isCodeInTextEmpath(textCodeProb, code);

		Logevent logeventSuccessful = new Logevent("SUCCESSFUL_TEXTCODEPROB_BOOLEAN_REQUESTED_BY_ID_WITH_PROGRAM: "
				+ program.toUpperCase() + "FOR_CODE: " + code.toUpperCase());
		logeventSuccessful.setTextCodeProbId(id);
		logeventService.create(logeventSuccessful);

		return isInText;
	}

	@RequestMapping(value = "program/{program}/categories/{category}/postId/{postId}", method = RequestMethod.GET)
	@ResponseBody
	public double getCategoryValueByPostId(@PathVariable String program, @PathVariable String category,
			@PathVariable Long postId) {

		Logevent logevent = new Logevent(program.toUpperCase() + "_CATEGORY_" + category.toUpperCase()
				+ "_REQUESTED_BY_POST_ID_" + postId.toString());
		logevent.setTextCodeProbId(textCodeProbService.findByPostID(postId).getPostID());
		logeventService.create(logevent);

		// validate input
		checkProgram(program);
		checkCategory(program, category);

		TextCodeProb textCodeProb = textCodeProbService.findByPostID(postId);

		if (textCodeProb == null) {
			log.error("ERR_BAD_PARAM_POSTID_INVALID");
			throw new BadParameterException("ERR_BAD_PARAM_POSTID_INVALID");
		}
		
		double categoryValue = getCategoryValue(textCodeProb, program, category);

		Logevent logeventSuccessful = new Logevent("SUCCESSFUL_" + program.toUpperCase() + "_CATEGORY_"
				+ category.toUpperCase() + "_REQUESTED_BY_POST_ID_" + postId.toString());
		logeventSuccessful.setTextCodeProbId(textCodeProbService.findByPostID(postId).getPostID());
		logeventService.create(logeventSuccessful);

		return categoryValue;
	}

	@RequestMapping(value = "program/{program}/categories/{category}/id/{id}", method = RequestMethod.GET)
	@ResponseBody
	public double getCategoryValueById(@PathVariable String program, @PathVariable String category,
			@PathVariable Long id) {

		Logevent logevent = new Logevent(
				program.toUpperCase() + "_CATEGORY_" + category.toUpperCase() + "_REQUESTED_BY_ID_" + id.toString());
		logevent.setTextCodeProbId(textCodeProbService.findById(id).getId());
		logeventService.create(logevent);

		// validate input
		checkProgram(program);
		checkCategory(program, category);

		TextCodeProb textCodeProb = textCodeProbService.findById(id);

		if (textCodeProb == null) {
			log.error("ERR_BAD_PARAM_ID_INVALID");
			throw new BadParameterException("ERR_BAD_PARAM_ID_INVALID");
		}

		double categoryValue = getCategoryValue(textCodeProb, program, category);

		Logevent logeventSuccessful = new Logevent("SUCCESSFUL_" + program.toUpperCase() + "_CATEGORY_"
				+ category.toUpperCase() + "_REQUESTED_BY_ID_" + id.toString());
		logeventSuccessful.setTextCodeProbId(textCodeProbService.findById(id).getId());
		logeventService.create(logeventSuccessful);

		return categoryValue;
	}

	@RequestMapping(value = "program/{program}/formula/code/{code}", method = RequestMethod.GET)
	@ResponseBody
	public boolean isFormulaAvailable(@PathVariable String program, @PathVariable String code) {

		logeventService.create(new Logevent("FORMULA_FOR_CODE_" + code.toString() + "_AVAILABLE_REQUESTED"));

		// validate input
		checkProgram(program);
		checkCode(code);

		boolean isAvailable = true;

		String formula = properties.getProperty("textcodeprob." + program.toLowerCase() + ".formula" + code);
		if (formula.isEmpty() || formula.equals("0"))
			isAvailable = false;

		logeventService.create(new Logevent("SUCCESSFUL_FORMULA_FOR_CODE_" + code.toString() + "_AVAILABLE_REQUESTED"));

		return isAvailable;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private TextCodeProb callApisCalculateProbs(TextCodeProb param) {

		TextCodeProb textCodeProb = new TextCodeProb();

		// Checking if text has already been analyzed. Then old database entry
		// is returned
		// if PostDate has changed -> update call APIs and calculate new
		// complete new entry -> call APIs and calculate
		if (textCodeProbService.existsByPostID(param.getPostID()) && (param.getPostDate() == null
				|| param.getPostDate().equals(textCodeProbService.findByPostID(param.getPostID()).getPostDate()))) {
			textCodeProb = textCodeProbService.findByPostID(param.getPostID());
		} else {
			if (textCodeProbService.existsByPostID(param.getPostID())
					&& !param.getPostDate().equals(textCodeProbService.findByPostID(param.getPostID()).getPostDate())) {
				textCodeProb = param;
				textCodeProb.setId(textCodeProbService.findByPostID(param.getPostID()).getId());
				textCodeProb = textCodeProbService.update(textCodeProb);
				Logevent logeventUpdate = new Logevent("TEXTCODEPROB_UPDATED");
				logeventUpdate.setTextCodeProbId(param.getId());
				logeventService.create(logeventUpdate);
			} else {
				textCodeProb = textCodeProbService.create(param);

				Logevent logeventTcpCreate = new Logevent("NEW_TEXTCODEPROB_CREATED");
				logeventTcpCreate.setTextCodeProbId(param.getId());
				logeventService.create(logeventTcpCreate);
			}

			Liwc liwc = new Liwc(param);
			liwc = liwcService.create(liwc);

			Logevent logeventLiwcCreate = new Logevent("NEW_LIWC_CREATED");
			logeventLiwcCreate.setLiwcId(liwc.getId());
			logeventService.create(logeventLiwcCreate);

			Empath empath = new Empath(param);
			empath = empathService.create(empath);

			Logevent logeventEmpathCreate = new Logevent("NEW_EMPATH_CREATED");
			logeventEmpathCreate.setEmpathId(empath.getId());
			logeventService.create(logeventEmpathCreate);

			// calls the API
			liwc = LiwcApiHandler.getLiwcScoresFromApi(liwc, param.getId(), param.getText());

			logeventService.create(new Logevent("LIWC_API_CALLED"));

			liwc = liwcService.update(liwc);

			Logevent logeventLiwcUpdate = new Logevent("LIWC_UPDATED");
			logeventLiwcUpdate.setLiwcId(liwc.getId());
			logeventService.create(logeventLiwcUpdate);

			// calls API
			empath = EmpathApiHandler.getEmpathScoresFromApi(empath, param.getText());

			logeventService.create(new Logevent("EMPATH_API_CALLED"));

			empath = empathService.update(empath);

			Logevent logeventEmpathUpdate = new Logevent("EMPATH_UPDATED");
			logeventEmpathUpdate.setEmpathId(empath.getId());
			logeventService.create(logeventEmpathUpdate);

			// calculates the probability of the text being assigned to the code
			textCodeProb = CodeProbCalculator.getProb(param, empath, liwc);

			logeventService.create(new Logevent("CODEPROB_CALCULATED"));

			textCodeProb = textCodeProbService.update(textCodeProb);

			Logevent logeventTcpUpdate = new Logevent("TEXTCODEPROB_UPDATED");
			logeventTcpUpdate.setTextCodeProbId(textCodeProb.getId());
			logeventService.create(logeventTcpUpdate);
		}
		return textCodeProb;
	}

	private double getCategoryValue(TextCodeProb textCodeProb, String program, String category) {
		double categoryValue = 0.0;
		if (program.equals("liwc")) {
			Liwc liwc = liwcService.findByTextCodeProb(textCodeProb);
			try {
				Object value = PropertyUtils.getProperty(liwc, category);
				categoryValue = (double) value;
			} catch (IllegalAccessException e) {
				log.error("ERR_TEXTCODEPROB_CONTROLLER_ILLEGAL_ACCESS_EXCEPTION");
				throw new TextCodeProbControllerException("ERR_TEXTCODEPROB_CONTROLLER_ILLEGAL_ACCESS_EXCEPTION");
			} catch (InvocationTargetException e) {
				log.error("ERR_TEXTCODEPROB_CONTROLLER_INVOCATION_TARGET_EXCEPTION");
				throw new TextCodeProbControllerException("ERR_TEXTCODEPROB_CONTROLLER_INVOCATION_TARGET_EXCEPTION");
			} catch (NoSuchMethodException e) {
				log.error("ERR_TEXTCODEPROB_CONTROLLER_NO_SUCH_METHOD_EXCEPTION");
				throw new TextCodeProbControllerException("ERR_TEXTCODEPROB_CONTROLLER_NO_SUCH_METHOD_EXCEPTION");
			}
		}
		if (program.equals("empath")) {
			Empath empath = empathService.findByTextCodeProb(textCodeProb);
			try {
				Object value = PropertyUtils.getProperty(empath, category);
				categoryValue = (double) value;
			} catch (IllegalAccessException e) {
				log.error("ERR_TEXTCODEPROB_CONTROLLER_ILLEGAL_ACCESS_EXCEPTION");
				throw new TextCodeProbControllerException("ERR_TEXTCODEPROB_CONTROLLER_ILLEGAL_ACCESS_EXCEPTION");
			} catch (InvocationTargetException e) {
				log.error("ERR_TEXTCODEPROB_CONTROLLER_INVOCATION_TARGET_EXCEPTION");
				throw new TextCodeProbControllerException("ERR_TEXTCODEPROB_CONTROLLER_INVOCATION_TARGET_EXCEPTION");
			} catch (NoSuchMethodException e) {
				log.error("ERR_TEXTCODEPROB_CONTROLLER_NO_SUCH_METHOD_EXCEPTION");
				throw new TextCodeProbControllerException("ERR_TEXTCODEPROB_CONTROLLER_NO_SUCH_METHOD_EXCEPTION");
			}
		}
		return categoryValue;
	}

	private static boolean isCodeInTextLiwc(TextCodeProb textCodeProb, String code) {

		Properties properties = PropertiesConfiguration.PropertyLoadHelper.loadProperties("application.properties");

		switch (code) {
		case ("0"):
			return textCodeProb.getLiwc_0() >= Double
					.parseDouble(properties.getProperty("textcodeprob.liwc.formula0.threshold"));
		case ("1"):
			return textCodeProb.getLiwc_1() >= Double
					.parseDouble(properties.getProperty("textcodeprob.liwc.formula1.threshold"));
		case ("2a"):
			return textCodeProb.getLiwc_2a() >= Double
					.parseDouble(properties.getProperty("textcodeprob.liwc.formula2a.threshold"));
		case ("2b"):
			return textCodeProb.getLiwc_2b() >= Double
					.parseDouble(properties.getProperty("textcodeprob.liwc.formula2b.threshold"));
		case ("3"):
			return textCodeProb.getLiwc_3() >= Double
					.parseDouble(properties.getProperty("textcodeprob.liwc.formula3.threshold"));
		case ("4"):
			return textCodeProb.getLiwc_4() >= Double
					.parseDouble(properties.getProperty("textcodeprob.liwc.formula4.threshold"));
		case ("5"):
			return textCodeProb.getLiwc_5() >= Double
					.parseDouble(properties.getProperty("textcodeprob.liwc.formula5.threshold"));
		case ("6a"):
			return textCodeProb.getLiwc_6a() >= Double
					.parseDouble(properties.getProperty("textcodeprob.liwc.formula6a.threshold"));
		case ("6b"):
			return textCodeProb.getLiwc_6b() >= Double
					.parseDouble(properties.getProperty("textcodeprob.liwc.formula6b.threshold"));
		case ("7"):
			return textCodeProb.getLiwc_7() >= Double
					.parseDouble(properties.getProperty("textcodeprob.liwc.formula7.threshold"));
		case ("84"):
			return textCodeProb.getLiwc_84() >= Double
					.parseDouble(properties.getProperty("textcodeprob.liwc.formula84.threshold"));
		case ("85"):
			return textCodeProb.getLiwc_85() >= Double
					.parseDouble(properties.getProperty("textcodeprob.liwc.formula85.threshold"));
		case ("9a"):
			return textCodeProb.getLiwc_9a() >= Double
					.parseDouble(properties.getProperty("textcodeprob.liwc.formula9a.threshold"));
		case ("9b"):
			return textCodeProb.getLiwc_9b() >= Double
					.parseDouble(properties.getProperty("textcodeprob.liwc.formula9b.threshold"));
		case ("10"):
			return textCodeProb.getLiwc_10() >= Double
					.parseDouble(properties.getProperty("textcodeprob.liwc.formula10.threshold"));
		case ("A1"):
			return textCodeProb.getLiwc_A1() >= Double
					.parseDouble(properties.getProperty("textcodeprob.liwc.formulaA1.threshold"));
		case ("A2"):
			return textCodeProb.getLiwc_A2() >= Double
					.parseDouble(properties.getProperty("textcodeprob.liwc.formulaA2.threshold"));
		case ("A3"):
			return textCodeProb.getLiwc_A3() >= Double
					.parseDouble(properties.getProperty("textcodeprob.liwc.formulaA3.threshold"));

		default:
			return false;
		}
	}

	public static boolean isCodeInTextEmpath(TextCodeProb textCodeProb, String code) {

		switch (code) {
		case ("0"):
			return textCodeProb.getEmpath_0() >= Double
					.parseDouble(properties.getProperty("textcodeprob.empath.formula0.threshold"));
		case ("1"):
			return textCodeProb.getEmpath_1() >= Double
					.parseDouble(properties.getProperty("textcodeprob.empath.formula1.threshold"));
		case ("2a"):
			return textCodeProb.getEmpath_2a() >= Double
					.parseDouble(properties.getProperty("textcodeprob.empath.formula2a.threshold"));
		case ("2b"):
			return textCodeProb.getEmpath_2b() >= Double
					.parseDouble(properties.getProperty("textcodeprob.empath.formula2b.threshold"));
		case ("3"):
			return textCodeProb.getEmpath_3() >= Double
					.parseDouble(properties.getProperty("textcodeprob.empath.formula3.threshold"));
		case ("4"):
			return textCodeProb.getEmpath_4() >= Double
					.parseDouble(properties.getProperty("textcodeprob.empath.formula4.threshold"));
		case ("5"):
			return textCodeProb.getEmpath_5() >= Double
					.parseDouble(properties.getProperty("textcodeprob.empath.formula5.threshold"));
		case ("6a"):
			return textCodeProb.getEmpath_6a() >= Double
					.parseDouble(properties.getProperty("textcodeprob.empath.formula6a.threshold"));
		case ("6b"):
			return textCodeProb.getEmpath_6b() >= Double
					.parseDouble(properties.getProperty("textcodeprob.empath.formula6b.threshold"));
		case ("7"):
			return textCodeProb.getEmpath_7() >= Double
					.parseDouble(properties.getProperty("textcodeprob.empath.formula7.threshold"));
		case ("84"):
			return textCodeProb.getEmpath_84() >= Double
					.parseDouble(properties.getProperty("textcodeprob.empath.formula84.threshold"));
		case ("85"):
			return textCodeProb.getEmpath_85() >= Double
					.parseDouble(properties.getProperty("textcodeprob.empath.formula85.threshold"));
		case ("9a"):
			return textCodeProb.getEmpath_9a() >= Double
					.parseDouble(properties.getProperty("textcodeprob.empath.formula9a.threshold"));
		case ("9b"):
			return textCodeProb.getEmpath_9b() >= Double
					.parseDouble(properties.getProperty("textcodeprob.empath.formula9b.threshold"));
		case ("10"):
			return textCodeProb.getEmpath_10() >= Double
					.parseDouble(properties.getProperty("textcodeprob.empath.formula10.threshold"));
		case ("A1"):
			return textCodeProb.getEmpath_A1() >= Double
					.parseDouble(properties.getProperty("textcodeprob.empath.formulaA1.threshold"));
		case ("A2"):
			return textCodeProb.getEmpath_A2() >= Double
					.parseDouble(properties.getProperty("textcodeprob.empath.formulaA2.threshold"));
		case ("A3"):
			return textCodeProb.getEmpath_A3() >= Double
					.parseDouble(properties.getProperty("textcodeprob.empath.formulaA3.threshold"));

		default:
			return false;
		}
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static boolean checkLanguage(String language) {

		if (!EnumUtils.isValidEnum(PromptingEnum_LanguageCodes.class, language)) {
			log.error("ERR_BAD_PARAM_INVALID_LANGUAGE_CODE");
			throw new BadParameterException("ERR_BAD_PARAM_INVALID_LANGUAGE_CODE");
		}
		return true;
	}

	public static boolean checkProgram(String program) {

		if (!EnumUtils.isValidEnum(TextEnum_Program.class, program)) {
			log.error("ERR_BAD_PARAM_PROGRAM_ILLEGAL");
			throw new BadParameterException("ERR_BAD_PARAM_PROGRAM_ILLEGAL");
		}
		return true;
	}

	public static boolean checkCategory(String program, String category) {
		if (program.equals("empath")) {
			if (!EnumUtils.isValidEnum(TextEnum_EmpathCategories.class, category)) {
				log.error("ERR_BAD_PARAM_INVALID_EMPATH_CATEGORY");
				throw new BadParameterException("ERR_BAD_PARAM_INVALID_EMPATH_CATEGORY");
			}
		}
		if (program.equals("liwc")) {
			if (!EnumUtils.isValidEnum(TextEnum_LiwcCategories.class, category)) {
				log.error("ERR_BAD_PARAM_INVALID_LIWC_CATEGORY");
				throw new BadParameterException("ERR_BAD_PARAM_INVALID_LIWC_CATEGORY");
			}
		}
		return true;
	}

	public static boolean checkCode(String code) {

		if (!(code.equals("0") || code.equals("1") || code.equals("2a") || code.equals("2b") || code.equals("3")
				|| code.equals("4") || code.equals("5") || code.equals("6a") || code.equals("6b") || code.equals("7")
				|| code.equals("84") || code.equals("85") || code.equals("9a") || code.equals("9b") || code.equals("10")
				|| code.equals("A1") || code.equals("A2") || code.equals("A3"))) {
			log.error("ERR_BAD_PARAM_CODE_ILLEGAL");
			throw new BadParameterException("ERR_BAD_PARAM_CODE_ILLEGAL");
		}
		return true;
	}
}