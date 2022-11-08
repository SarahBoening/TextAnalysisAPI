package de.rub.iaw.service.ldap;

import de.rub.iaw.domain.Group;

public interface LdapGroupService {
	
	public void create(Group group);
	
	public void update(Group group);
	
	public Group findByName(String groupName);

}
