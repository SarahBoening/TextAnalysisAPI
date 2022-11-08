package de.rub.iaw.listener;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.stereotype.Component;

import de.rub.iaw.domain.Logevent;
import de.rub.iaw.domain.User;
import de.rub.iaw.service.db.LogeventService;
import de.rub.iaw.service.db.UserService;
import de.rub.iaw.service.ldap.ActiveDirectoryLdapQueryService;
import de.rub.iaw.util.AuthenticationMethodCheck;

@Component
public class AuthenticationListener implements ApplicationListener<InteractiveAuthenticationSuccessEvent> {

	@Autowired
	LogeventService logeventService;

	@Autowired
	ActiveDirectoryLdapQueryService activeDirectoryLdapQueryService;

	@Autowired
	UserService userService;

	@Override
	public void onApplicationEvent(InteractiveAuthenticationSuccessEvent arg0) {

		if (AuthenticationMethodCheck.isActiveDirectory()) {
			SecurityContext secContext = SecurityContextHolder.getContext();
			Authentication auth = secContext.getAuthentication();

			// check whether user already exists in content-db or if its the
			// first login...
			User userToCheck = userService.findByUsername(auth.getName());

			// ...otherwise create new user
			if (userToCheck == null) {
				userToCheck = new User();

				LdapUserDetailsImpl p = (LdapUserDetailsImpl) secContext.getAuthentication().getPrincipal();

				// get first name, last name, full name and email from AD (via
				// LDAP)
				userToCheck = activeDirectoryLdapQueryService.getDetailsFromActiveDirectoryLdap(p.getDn());

				// set missing values
				userToCheck.setUsername(auth.getName());
				userToCheck.setPassword(""); // empty because we dont get password details from LDAP
				userToCheck.setEmail(auth.getName());
				userToCheck.setTimestamp(new Date());

				// save it
				userToCheck = userService.create(userToCheck);

				// log it
				logeventService.create(new Logevent(userToCheck,
						"NEW_USER_CREATED"));
			}

			logeventService.create(new Logevent(userToCheck, "USER_LOGGED_IN"));
		} else {
			logeventService.create(new Logevent(userService.getCurrentUser(), "USER_LOGGED_IN"));
		}
	}
}
