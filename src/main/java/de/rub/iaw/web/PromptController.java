package de.rub.iaw.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.rub.iaw.domain.Logevent;
import de.rub.iaw.domain.Prompt;
import de.rub.iaw.exception.BadParameterException;
import de.rub.iaw.service.db.LogeventService;
import de.rub.iaw.service.db.PromptService;
import de.rub.iaw.util.InputValidator;
import de.rub.iaw.util.PromptingEnum_Category;
import de.rub.iaw.util.PromptingEnum_Instructional_Character;
import de.rub.iaw.util.PromptingEnum_LanguageCodes;
import de.rub.iaw.util.PromptingEnum_Location;
import de.rub.iaw.util.PromptingEnum_Target;
import edu.emory.mathcs.backport.java.util.Arrays;

@RestController
@RequestMapping(value = "/prompt")
public class PromptController {

	@Autowired
	PromptService promptService;

	@Autowired
	LogeventService logeventService;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public void create(@ModelAttribute Prompt param) {

		// validate input
		checkPromptInput(param);

		// save prompt
		param = promptService.create(param);

		// log
		Logevent logevent = new Logevent("NEW_PROMPT_CREATED");
		logevent.setPromptId(param.getId());
		logeventService.create(logevent);
	}

	@RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
	@ResponseBody
	public void update(@ModelAttribute Prompt param) {

		// validate input
		checkPromptInput(param);

		// load old version
		Prompt old = promptService.findById(param.getId());

		// update values
		old.setCategory(param.getCategory());
		old.setSetting(param.getSetting());
		old.setInstructionalCharacter(param.getInstructionalCharacter());
		old.setString_croatian(param.getString_croatian());
		old.setString_english(param.getString_english());
		old.setString_german(param.getString_german());
		old.setString_slovenian(param.getString_slovenian());

		// save
		old = promptService.update(old);

		// log
		Logevent logevent = new Logevent("PROMPT_UPDATED");
		logevent.setPromptId(old.getId());
		logeventService.create(logevent);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List<Prompt> getAllPrompts() {
		return promptService.findAll();
	}

	@RequestMapping(value = "/goal/{goal}", method = RequestMethod.GET)
	public List<Prompt> getByGoalAndLanguage(@PathVariable String goal,
			@RequestParam(value = "l", defaultValue = "en") String language) {
		checkLanguageEnabled(language);
		checkStringInput(goal);

		logeventService.create(new Logevent("PROMPTS_REQUESTED_BY_GOAL: " + goal + " || LANGUAGE: " + language));

		return promptService.findByGoal(goal);
	}

	@RequestMapping(value = "/location/{location}", method = RequestMethod.GET)
	public List<Prompt> getByLocationAndLanguage(@PathVariable String location,
			@RequestParam(value = "l", defaultValue = "en") String language) {
		checkLanguageEnabled(language);
		checkStringInput(location);

		logeventService
				.create(new Logevent("PROMPTS_REQUESTED_BY_LOCATION: " + location + " || LANGUAGE: " + language));

		return promptService.findByLocation(location);
	}

	@RequestMapping(value = "/target/{target}", method = RequestMethod.GET)
	public List<Prompt> getByTargetAndLanguage(@PathVariable String target,
			@RequestParam(value = "l", defaultValue = "en") String language) {
		checkLanguageEnabled(language);
		checkStringInput(target);

		logeventService.create(new Logevent("PROMPTS_REQUESTED_BY_TARGET: " + target + " || LANGUAGE: " + language));

		return promptService.findByTarget(target);
	}

	@RequestMapping(value = "/setting/{setting}", method = RequestMethod.GET)
	public List<Prompt> getBySettingAndLanguage(@PathVariable String setting,
			@RequestParam(value = "l", defaultValue = "en") String language) {
		checkLanguageEnabled(language);
		checkStringInput(setting);

		logeventService.create(new Logevent("PROMPTS_REQUESTED_BY_SETTING: " + setting + " || LANGUAGE: " + language));

		return promptService.findBySetting(setting);
	}

	@RequestMapping(value = "/category/{category}", method = RequestMethod.GET)
	public List<Prompt> getByCategoryAndLanguage(@PathVariable String category,
			@RequestParam(value = "l", defaultValue = "en") String language) {
		checkLanguageEnabled(language);
		checkStringInput(category);

		logeventService
				.create(new Logevent("PROMPTS_REQUESTED_BY_CATEGORY: " + category + " || LANGUAGE: " + language));

		return promptService.findBySetting(category);
	}

	@RequestMapping(value = "/goal/{goal}/location/{location}", method = RequestMethod.GET)
	public List<Prompt> getByGoalAndLocationAndLanguage(@PathVariable String goal, @PathVariable String location,
			@RequestParam(value = "l", defaultValue = "en") String language) {
		checkLanguageEnabled(language);
		checkStringInput(goal);
		checkStringInput(location);

		logeventService.create(new Logevent(
				"PROMPTS_REQUESTED_BY_GOAL: " + goal + " || LOCATION: " + location + " || LANGUAGE: " + language));

		return promptService.findByGoalAndLocation(goal, location);
	}

	@RequestMapping(value = "/goal/{goal}/location/{location}/target/{target}", method = RequestMethod.GET)
	public List<Prompt> getByGoalAndLocationAndTargetAndLanguage(@PathVariable String goal,
			@PathVariable String location, @PathVariable String target,
			@RequestParam(value = "l", defaultValue = "en") String language) {
		checkLanguageEnabled(language);
		checkStringInput(goal);
		checkStringInput(location);
		checkStringInput(target);

		logeventService.create(new Logevent("PROMPTS_REQUESTED_BY_GOAL: " + goal + " || LOCATION: " + location
				+ " || TARGET: " + target + " || LANGUAGE: " + language));

		return promptService.findByGoalAndLocationAndTarget(goal, location, target);
	}

	@RequestMapping(value = "/goal/{goal}/location/{location}/target/{target}/instructionalcharacter/{instructional_character}", method = RequestMethod.GET)
	public List<Prompt> getByGoalAndLocationAndTargetAndInstructionalCharacterLanguage(@PathVariable String goal,
			@PathVariable String location, @PathVariable String target, @PathVariable String instructional_character,
			@RequestParam(value = "l", defaultValue = "en") String language) {
		checkLanguageEnabled(language);
		checkStringInput(goal);
		checkStringInput(location);
		checkStringInput(target);
		checkStringInput(instructional_character);

		logeventService.create(new Logevent(
				"PROMPTS_REQUESTED_BY_GOAL: " + goal + " || LOCATION: " + location + " || TARGET: " + target
						+ " || LANGUAGE: " + language + " || INSTRUCTIONAL_CHARACTER: " + instructional_character));

		return promptService.findByGoalAndLocationAndTargetAndInstructionalCharacter(goal, location, target,
				instructional_character);
	}

	@RequestMapping(value = "/goal/{goal}/location/{location}/target/{target}/category/{category}", method = RequestMethod.GET)
	public List<Prompt> getByGoalAndLocationAndTargetAndCategoryAndLanguage(@PathVariable String goal,
			@PathVariable String location, @PathVariable String target, @PathVariable String category,
			@RequestParam(value = "l", defaultValue = "en") String language) {
		checkLanguageEnabled(language);
		checkStringInput(goal);
		checkStringInput(location);
		checkStringInput(target);
		checkStringInput(category);

		logeventService.create(new Logevent("PROMPTS_REQUESTED_BY_GOAL: " + goal + " || LOCATION: " + location
				+ " || TARGET: " + target + " || CATEGORY: " + category + " || LANGUAGE: " + language));

		return promptService.findByGoalAndLocationAndTargetAndCategory(goal, location, target, category);
	}

	@RequestMapping(value = "/getCategoryList")
	public List<String> getListOfCategories() {
		return Arrays.asList(PromptingEnum_Category.values());
	}

	@RequestMapping(value = "/getLanguageList")
	public List<String> getListOfLanguages() {
		return Arrays.asList(PromptingEnum_LanguageCodes.values());
	}

	@RequestMapping(value = "/getLocationList")
	public List<String> getListOfLocations() {
		return Arrays.asList(PromptingEnum_Location.values());
	}

	@RequestMapping(value = "/getTargetList")
	public List<String> getListOfTargets() {
		return Arrays.asList(PromptingEnum_Target.values());
	}

	public static List<String> getPromptsAsList(List<Prompt> listOfPrompts, String language) {
		List<String> promptsAsStrings = new ArrayList<>();

		for (Prompt prompt : listOfPrompts) {
			promptsAsStrings.add(prompt.getTranslation(language));
		}

		return promptsAsStrings;
	}

	/**
	 * Used to check all given inputs to avoid default 404, which is given if
	 * someone asks for something with an empty input
	 * 
	 * @param param
	 * @return
	 */
	public static Boolean checkStringInput(String param) {
		if (!InputValidator.ValidateString(param)) {
			throw new BadParameterException("ERR_BAD_PARAM");
		}

		return true;
	}

	/**
	 * Checks whether the following input fields are null or full of blanks:
	 * goal
	 * 
	 * @param prompt
	 * @return true if values are ok
	 */
	public static Boolean checkPromptInput(Prompt prompt) {

		// Check goals
		if (!InputValidator.ValidateString(prompt.getGoal())) {
			throw new BadParameterException("ERR_BAD_PARAM_PROMPT_GOAL_MISSING");
		}

		// Validate that at least one language is specified
		if (InputValidator.ValidateString(prompt.getString_croatian())
				|| InputValidator.ValidateString(prompt.getString_english())
				|| InputValidator.ValidateString(prompt.getString_german())
				|| InputValidator.ValidateString(prompt.getString_slovenian())) {
			throw new BadParameterException("ERR_BAD_PARAM_PROMPT_MISSING_ANY_LANGUAGE");
		}

		checkCategoryCode(prompt.getCategory());
		checkTargetCode(prompt.getTarget());
		checkLocationCode(prompt.getLocation());
		checkInstructionalCharacterCode(prompt.getInstructionalCharacter());

		return true;
	}

	/**
	 * Check whether given category code is existing
	 * 
	 * @param category
	 * @return
	 */
	public static Boolean checkCategoryCode(String category) {
		if (!EnumUtils.isValidEnum(PromptingEnum_Category.class, category)) {
			throw new BadParameterException("ERR_BAD_PARAM_PROMPT_CATEGORY_ILLEGAL");
		}

		return true;
	}

	/**
	 * Check whether given target code is existing
	 * 
	 * @param target
	 * @return
	 */
	public static Boolean checkTargetCode(String target) {
		if (!EnumUtils.isValidEnum(PromptingEnum_Target.class, target)) {
			throw new BadParameterException("ERR_BAD_PARAM_PROMPT_TARGET_ILLEGAL");
		}

		return true;
	}

	/**
	 * Check whether given location code is existing
	 * 
	 * @param location
	 * @return
	 */
	public static Boolean checkLocationCode(String location) {
		if (!EnumUtils.isValidEnum(PromptingEnum_Location.class, location)) {
			throw new BadParameterException("ERR_BAD_PARAM_PROMPT_LOCATION_ILLEGAL");
		}

		return true;
	}

	/**
	 * Checks whether a given input language code is contained in the enum
	 * 
	 * @param language
	 * @return
	 */
	public static Boolean checkLanguageEnabled(String language) {
		if (!EnumUtils.isValidEnum(PromptingEnum_LanguageCodes.class, language)) {
			throw new BadParameterException("ERR_BAD_PARAM_INVALID_LANGUAGE_CODE");
		}
		return true;
	}

	/**
	 * Checks whether a given input instructional character code is contained in
	 * the enum
	 * 
	 * @param instructional_character
	 * @return
	 */
	public static Boolean checkInstructionalCharacterCode(String instructional_character) {
		if (!EnumUtils.isValidEnum(PromptingEnum_Instructional_Character.class, instructional_character)) {
			throw new BadParameterException("ERR_BAD_PARAM_PROMPT_INSTRUCTIONAL_CHARACTER_ILLEGAL");
		}

		return true;
	}
}