package de.rub.iaw.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.rub.iaw.domain.Logevent;
import de.rub.iaw.exception.BadParameterException;
import de.rub.iaw.service.db.LogeventService;
import de.rub.iaw.util.InputValidator;

@RestController
@RequestMapping(value = "/log")
public class LogeventController {

	@Autowired
	LogeventService logeventService;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public void create(@ModelAttribute Logevent param) {

		checkInput(param);

		// TODO Check whether IDs are realistic when set!

		// TODO error if no ID at all is set? to force frontend devs to set ids
		// ..=> better logs :)

		logeventService.create(param);
	}

	public static Boolean checkInput(Logevent logevent) {

		if (!InputValidator.ValidateString(logevent.getDescription())) {
			throw new BadParameterException("ERR_BAD_PARAM_LOG_DESCRIPTION_MISSING");
		}

		return true;
	}

}
