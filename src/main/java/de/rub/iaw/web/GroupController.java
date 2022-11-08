package de.rub.iaw.web;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.rub.iaw.domain.Group;
import de.rub.iaw.exception.AlreadyExistException;
import de.rub.iaw.exception.BadParameterException;
import de.rub.iaw.service.db.GroupService;
import de.rub.iaw.service.ldap.LdapGroupService;
import de.rub.iaw.util.AuthenticationMethodCheck;
import de.rub.iaw.util.InputValidator;

@RestController
@RequestMapping(value = "/group")
public class GroupController {

	@Autowired
	private GroupService groupService;

	@Autowired
	private LdapGroupService ldapGroupService;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public void create(@ModelAttribute Group param) {

		checkInput(param);

		param.setTimestamp(new Date());

		// add to ldap
		if (AuthenticationMethodCheck.isLDAP()) {
			try {
				ldapGroupService.create(param);
			} catch (org.springframework.ldap.NameAlreadyBoundException e) {
				throw new AlreadyExistException("ERR_ALREADY_EXISTS_GROUP");
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}

		// add to content db
		groupService.create(param);
	}

	@RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
	@ResponseBody
	public void update(@ModelAttribute Group param) {
		// no password check, will be admin only method!

		checkInput(param);

		Group existingGroup = groupService.findById(param.getId());

		existingGroup.setName(param.getName());
		existingGroup.setPassword(param.getPassword());

		groupService.update(existingGroup);

		// TODO Update group in LDAP as well
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody Group findById(@PathVariable Long id) {
		return groupService.findById(id);
	}

	/**
	 * Checks if name & password are empty, null, or full of blanks
	 * 
	 * @param group
	 * @return true if values are ok
	 * @throws BadParameterException
	 *             Error code shows broken parameter
	 */
	public static Boolean checkInput(Group group) {

		checkGroupName(group.getName());

		if (!InputValidator.ValidatePassword(group.getPassword())) {
			throw new BadParameterException("ERR_BAD_PARAM_GROUP_PASSWORD");
		}

		return true;
	}

	public static Boolean checkGroupName(String name) {

		if (!InputValidator.ValidateString(name)) {
			throw new BadParameterException("ERR_BAD_PARAM_GROUP_NAME");
		}
		return true;
	}
}
