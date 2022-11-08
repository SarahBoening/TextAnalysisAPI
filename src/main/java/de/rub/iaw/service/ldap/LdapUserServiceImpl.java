package de.rub.iaw.service.ldap;

import java.util.List;

import javax.naming.Name;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.stereotype.Service;

import de.rub.iaw.LdapSecurityConfiguration;
import de.rub.iaw.domain.User;

@Service
/*
 * Use this class when incorporating the LDAP Authentication (Without Active Directory!)
 */
public class LdapUserServiceImpl implements LdapUserService {
	
	// put all users into ou=people and use uid attribute to ensure uniqueness of login names
	public static final String BASE_DN_USER = "ou=people";
	
	@Autowired
	private LdapTemplate ldapTemplate;
	
	// Build distinguished name for ldap 
	public static Name buildDn(String username) {
		return LdapNameBuilder.newInstance(BASE_DN_USER)
				.add("uid", username)
				.build();
	}
	
	public static Name buildFullDn(String username) {
		String dn = BASE_DN_USER + "," + LdapSecurityConfiguration.BASE_DN_PARTITION;
		return LdapNameBuilder.newInstance(dn)
				.add("uid", username)
				.build();
	}
	
	@Override
	public void create(User user) {
		Name dn = buildDn(user.getUsername());
		
		DirContextAdapter context = new DirContextAdapter(dn);
		
		context.setAttributeValues("objectclass", new String[] {"person", "inetOrgPerson", "organizationalPerson", "top"});
		context.setAttributeValue("gn", user.getFirstname());
		context.setAttributeValue("sn", user.getLastname());
		context.setAttributeValue("cn", user.getFullname());
		context.setAttributeValue("uid", user.getUsername());
		context.setAttributeValue("userPassword", user.getPassword());
		context.setAttributeValue("mail", user.getEmail());
		
		ldapTemplate.bind(context); // bind, rebind, unbind...	
	}

	@Override
	public void update(User user) {
		// TODO Auto-generated method stub
	}

	@Override
	public User findByUsername(String username) {
		Name dn = buildDn(username);
		return ldapTemplate.lookup(dn, new PersonContextMapper());
	}

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Boolean addUserToGroup(String username, String groupname) {
		Name groupDn = LdapGroupServiceImpl.buildDn(groupname);
		Name userDn = LdapUserServiceImpl.buildFullDn(username);
		
		ModificationItem[] modItems = new ModificationItem[] {
			new ModificationItem(DirContext.ADD_ATTRIBUTE, new BasicAttribute("member", userDn.toString()))	
		};
		
		ldapTemplate.modifyAttributes(groupDn, modItems);
		
		return true;
	}

	/**
	 * Used to for lookup methods. Ldap data is retrieved and mapped into a LdapUser object
	 */
	private static class PersonContextMapper implements ContextMapper<User> {
		
		@Override
		public User mapFromContext(Object ctx) {
			DirContextAdapter context = (DirContextAdapter) ctx;
			User user = new User();
			
			user.setFirstname(context.getStringAttribute("gn"));
			user.setLastname(context.getStringAttribute("sn"));
			user.setFullname(context.getStringAttribute("cn"));
			user.setUsername(context.getStringAttribute("uid"));
			user.setEmail(context.getStringAttribute("mail"));
			
			return user;
			// user.setDn(context.getDn()); do i need the dn?
			// do i need the password?
		}
	}
	
}
