package de.rub.iaw.service.db;

import javax.annotation.Resource;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.rub.iaw.domain.User;
import de.rub.iaw.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Resource
	private UserRepository userRepository;

	@Override
	@Transactional("contentdbTransactionManager")
	public User create(User user) {
		return userRepository.save(user);
	}

	@Override
	@Transactional("contentdbTransactionManager")
	public User update(User user) {
		return userRepository.save(user);
	}

	@Override
	@Transactional("contentdbTransactionManager")
	public User findById(Long id) {
		return userRepository.findOne(id);
	}

	@Override
	@Transactional("contentdbTransactionManager")
	public Iterable<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	@Transactional("contentdbTransactionManager")
	public User getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		return this.findByUsername(auth.getName());
	}

	@Override
	@Transactional("contentdbTransactionManager")
	public User findByUsername(String username) {
		return userRepository.findByUsernameIgnoreCase(username);
	}
}
