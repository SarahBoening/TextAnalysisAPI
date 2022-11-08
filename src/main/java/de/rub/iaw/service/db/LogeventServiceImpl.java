package de.rub.iaw.service.db;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;

import de.rub.iaw.domain.Logevent;
import de.rub.iaw.repository.LogeventRepository;

@Service
public class LogeventServiceImpl implements LogeventService {

	@Resource
	private LogeventRepository logeventRepository;

	@Autowired
	private UserService userService;

	@Override
	@Transactional("contentdbTransactionManager")
	public Logevent create(Logevent logevent) {

		logevent.setTimestamp(new Date());

		// check for session first, it will be created if not existing!
		// Get session ID only if user is not attempting to logout, otherwise a
		// new session will be created!
		// http://stackoverflow.com/questions/3542026/retrieving-session-id-with-spring-security
		if (logevent.getSessionId() == null && (logevent.getDescription() != "SESSION_EXPIRED")) {
			logevent.setSessionId(RequestContextHolder.currentRequestAttributes().getSessionId());
		}

		// if user is empty and session is not expired, then set user!
		// When session is expired, the username is no longer obtainable
		if (logevent.getUser() == null && (logevent.getDescription() != "SESSION_EXPIRED")) {
			logevent.setUser(userService.getCurrentUser());
		}

		// if instance is still empty, set it to _DEFAULT_
		// TODO How to message instance to system? Via user login as third
		// field?
		if (logevent.getInstance() == null) {
			logevent.setInstance("_DEFAULT_");
		}

		return logeventRepository.save(logevent);
	}

}
