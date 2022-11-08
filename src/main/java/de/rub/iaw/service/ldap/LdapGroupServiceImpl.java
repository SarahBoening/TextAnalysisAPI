package de.rub.iaw.service.ldap;

import javax.naming.Name;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.stereotype.Service;

import de.rub.iaw.LdapSecurityConfiguration;
import de.rub.iaw.domain.Group;

@Service
/*
 * Use this class when incorporating the LDAP Authentication (Without Active Directory!)
 */
public class LdapGroupServiceImpl implements LdapGroupService {

	public static final String BASE_DN_GROUPS = "ou=groups";
	
	@Autowired
	private LdapTemplate ldapTemplate;
	
	// Build distinguished name for ldap 
	public static Name buildDn(String groupname) {
		return LdapNameBuilder.newInstance(BASE_DN_GROUPS)
				.add("cn", groupname)
				.build();
	}
	
	public static Name buildFullDn(String groupname) {
		String dn = BASE_DN_GROUPS + "," + LdapSecurityConfiguration.BASE_DN_PARTITION;
		return LdapNameBuilder.newInstance(dn)
				.add("cn", groupname)
				.build();
	}
	
	@Override
	public void create(Group group) {
		Name dn = buildDn(group.getName());
		
		DirContextAdapter context = new DirContextAdapter(dn);
		
		context.setAttributeValues("objectclass", new String[] {"groupOfNames", "top"});
		context.setAttributeValue("cn", group.getName());
		context.setAttributeValue("member", "");
				
		// TODO How to save parent id and password?
		
		ldapTemplate.bind(context);
	}

	@Override
	public void update(Group group) {
		// TODO Auto-generated method stub
	}

	@Override
	public Group findByName(String groupName) {
		Name dn = buildDn(groupName);
		return ldapTemplate.lookup(dn, new GroupContextMapper());
	}
	
	private static class GroupContextMapper implements ContextMapper<Group>{

		@Override
		public Group mapFromContext(Object ctx) {
			DirContextAdapter context = (DirContextAdapter) ctx;
			Group group = new Group();
			group.setName(context.getStringAttribute("cn"));
			group.setPassword(context.getStringAttribute("description"));

			//TODO: How to get/set users?
			
			return group;
		}	
	}
}
