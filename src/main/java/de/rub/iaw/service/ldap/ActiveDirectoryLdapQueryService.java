package de.rub.iaw.service.ldap;

import de.rub.iaw.domain.User;

public interface ActiveDirectoryLdapQueryService {
	
	public User getDetailsFromActiveDirectoryLdap(String username);
	
}
