package de.rub.iaw.service.ldap;

import javax.naming.Name;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.stereotype.Service;

import de.rub.iaw.domain.User;

@Service
/*
 * This service has to be used when incorporating the ActiveDirectory Authentication Method!
 * Service is used to query missing details to complete user profile (e.g. email, first name, etc.)
 */
public class ActiveDirectoryLdapQueryServiceImpl implements ActiveDirectoryLdapQueryService {

	// TODO Get this from environment
	protected static final String baseDN = "OU=Staff,OU=IMTM";
	protected static final String LDAP_QUERY_PARAMETER = "DN"; // "uid" for the other ldap
	
	@Autowired
	private LdapTemplate ldapTemplate;
	
	@Override
	public User getDetailsFromActiveDirectoryLdap(String username) {
		Name dn = LdapNameBuilder.newInstance(username).build();
		
		return ldapTemplate.lookup(dn, new PersonContextMapper());
	}
	
	/**
	 * Used to for lookup methods. Ldap data is retrieved and mapped into a LdapUser object
	 */
	private static class PersonContextMapper implements ContextMapper<User> {
		
		@Override
		public User mapFromContext(Object ctx) {
			DirContextAdapter context = (DirContextAdapter) ctx;
			User user = new User();
			
			user.setFirstname(context.getStringAttribute("givenName"));
			user.setLastname(context.getStringAttribute("sn"));
			user.setFullname(context.getStringAttribute("cn"));
			user.setEmail(context.getStringAttribute("mail"));
			
			return user;
		}
	}
}
