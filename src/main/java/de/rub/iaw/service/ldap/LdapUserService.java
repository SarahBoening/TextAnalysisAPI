package de.rub.iaw.service.ldap;

import java.util.List;

import de.rub.iaw.domain.User;

public interface LdapUserService {

	public void create(User user);
	
	public void update(User user);
		
	public User findByUsername(String username);
	
	public List<User> findAll();
	
	public Boolean addUserToGroup(String username, String groupname);
	
}
