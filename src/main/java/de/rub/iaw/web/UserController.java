package de.rub.iaw.web;

import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.rub.iaw.domain.Group;
import de.rub.iaw.domain.Logevent;
import de.rub.iaw.domain.User;
import de.rub.iaw.exception.AlreadyExistException;
import de.rub.iaw.exception.BadParameterException;
import de.rub.iaw.exception.EntryDoesNotExistException;
import de.rub.iaw.exception.UnauthorizedException;
import de.rub.iaw.service.db.GroupService;
import de.rub.iaw.service.db.LogeventService;
import de.rub.iaw.service.db.UserService;
import de.rub.iaw.service.ldap.LdapGroupService;
import de.rub.iaw.service.ldap.LdapUserService;
import de.rub.iaw.util.AuthenticationMethodCheck;
import de.rub.iaw.util.InputValidator;

@RestController
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private GroupService groupService;

	@Autowired
	private LdapUserService ldapUserService;

	@Autowired
	private LdapGroupService ldapGroupService;

	@Autowired
	private LogeventService logeventService;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public void create(@ModelAttribute User param) {

		// check input first
		checkInput(param);

		param = checkNames(param);

		// set timestamp
		param.setTimestamp(new Date());

		// add user to ldap if login method is LDAP
		if (AuthenticationMethodCheck.isLDAP()) {
			try {
				ldapUserService.create(param);
			} catch (org.springframework.ldap.NameAlreadyBoundException e) {
				throw new AlreadyExistException("ERR_ALREADY_EXISTS_USERNAME");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		// TODO insert pw hash from ldap into mysql

		// and now add user to content db
		User savedUser = userService.create(param);

		Logevent l = new Logevent("NEW_USER_CREATED");
		l.setUser(savedUser);
		logeventService.create(l);
	}

	@RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
	public @ResponseBody void update(@ModelAttribute User param, @PathVariable Long id) {

		checkInput(param);

		param = checkNames(param);

		// check if user exists
		User existingUser = userService.findById(id);

		if (existingUser == null) {
			throw new EntryDoesNotExistException("ERR_DOES_NOT_EXIST_USER");
		}

		// TODO update user in ldap

		existingUser.setEmail(param.getEmail());
		existingUser.setPassword(param.getPassword());
		userService.update(existingUser);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody User findById(@PathVariable Long id) {

		logeventService.create(new Logevent("Find_User_By_ID " + id));

		User user = userService.findById(id);
		return user;
	}

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody User getUsername() {
		logeventService.create(new Logevent("GET_CURRENT_USER"));

		return userService.getCurrentUser();
	}

	@RequestMapping(value = "/join/{param}", method = RequestMethod.POST)
	@ResponseBody
	public void joinGroup(@ModelAttribute Group param) {

		GroupController.checkInput(param);

		// check if password is correct
		Group group = groupService.findByName(param.getName());
		String correctPassword = group.getPassword();

		if (!correctPassword.equals(param.getPassword()))
			throw new UnauthorizedException("ERR_UNAUTHORIZED_WRONG_PASSWORD");

		// get current user
		User currentUser = userService.getCurrentUser();

		// Add to LDAP if login method is LDAP
		if (AuthenticationMethodCheck.isLDAP()) {
			try {
				ldapUserService.addUserToGroup(currentUser.getUsername(), param.getName());
			} catch (org.springframework.ldap.AttributeInUseException e) {
				throw new AlreadyExistException("ERR_ALREADY_EXISTS_USER_IN_GROUP");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

		// add group to groups of current user
		Set<Group> groupsOfCurrentUser = currentUser.getGroups();
		groupsOfCurrentUser.add(group);
		currentUser.setGroups(groupsOfCurrentUser);

		System.out.println(currentUser.getGroups().toString());

		// write change
		userService.update(currentUser);

		// log change
		Logevent l = new Logevent("USER_JOINED_GROUP");
		l.setUser(currentUser);
		l.setGroup(group);
		logeventService.create(l);
	}

	@RequestMapping(value = "/leave/{param}", method = RequestMethod.POST)
	public void leaveGroup(@PathVariable String param) {

		GroupController.checkGroupName(param);

		Group GroupToLeave = groupService.findByName(param);

		// get user and his groups
		User currentUser = userService.getCurrentUser();
		Set<Group> allGroupsOfCurrentUser = currentUser.getGroups();

		// remove group
		allGroupsOfCurrentUser.remove(GroupToLeave);

		// save user
		currentUser.setGroups(allGroupsOfCurrentUser);
		userService.update(currentUser);

		// write log
		Logevent l = new Logevent("USER_LEFT_GROUP");
		l.setUser(currentUser);
		l.setGroup(GroupToLeave);
		logeventService.create(l);
	}

	/**
	 * Returns all groups of the current user
	 * 
	 * @return Set of Groups
	 */
	@RequestMapping(value = "/getgroups", method = RequestMethod.GET)
	public Set<Group> getGroupsOfCurrentUser() {
		return userService.getCurrentUser().getGroups();
	}

	/**
	 * Returns all groups of a given user
	 * 
	 * @param id
	 *            of the user
	 * @return Set of Groups
	 */
	@RequestMapping(value = "/{id}/getgroups", method = RequestMethod.GET)
	public Set<Group> getGroupsByUserID(@PathVariable Long id) {
		return userService.findById(id).getGroups();
	}

	/**
	 * This method helps setting cn, sn (both mandatory in ldap)
	 * 
	 * @param user
	 * @return updated user
	 */
	public static User checkNames(User user) {

		// Fix optional user fields
		// if first and last name are set, build full name
		// else: use username for mandatory ldap fields cn, sn
		if (InputValidator.ValidateString(user.getFirstname()) && (InputValidator.ValidateString(user.getLastname()))) {
			user.setFullname(user.getFirstname() + " " + user.getLastname());
		} else {
			// if last name is given, use it, otherwise copy username
			if (!InputValidator.ValidateString(user.getLastname())) {
				user.setLastname(user.getUsername());
			}

			// since first name is missing, use username as fullname
			user.setFullname(user.getUsername());
		}

		return user;
	}

	/**
	 * Checks if input fields username, password, email are null, empty, only
	 * blanks
	 * 
	 * @param user
	 * @return true if values are ok
	 * @throws BadParameterException
	 *             Error code shows broken parameter
	 */
	public static Boolean checkInput(User user) {

		if (!InputValidator.ValidateString(user.getUsername())) {
			throw new BadParameterException("ERR_BAD_PARAM_USER_USERNAME_MISSING");
		}

		if (!InputValidator.ValidateString(user.getPassword())) {
			throw new BadParameterException("ERR_BAD_PARAM_USER_PASSWORD_MISSING");
		}

		if (!InputValidator.ValidateString(user.getEmail())) {
			throw new BadParameterException("ERR_BAD_PARAM_USER_EMAIL_MISSING");
		}

		return true;
	}
}
