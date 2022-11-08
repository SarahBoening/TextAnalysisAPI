package de.rub.iaw.service.db;

import de.rub.iaw.domain.User;

public interface UserService {

	// LdapService uses this method to create user in content db!
	public User create(User user);

	public User update(User user);

	// We don't want to delete :)

	public User findById(Long id);

	public User findByUsername(String username);

	public Iterable<User> findAll();

	public User getCurrentUser();

}
