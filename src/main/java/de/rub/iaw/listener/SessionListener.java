package de.rub.iaw.listener;

import javax.servlet.http.HttpSessionEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.stereotype.Component;

import de.rub.iaw.domain.Logevent;
import de.rub.iaw.service.db.LogeventService;

@Component
public class SessionListener extends HttpSessionEventPublisher {

	@Autowired
	LogeventService logeventService;
	
	@Override
	public void sessionCreated(HttpSessionEvent event) {
		
		// set max inactive time
		event.getSession().setMaxInactiveInterval(60*10);
		
		// Somehow this isnt triggered upon login. Therefore login loggin is at the authentication listener
		// Additionally this is triggered upon invalid login attempt. Commented out to prevent errors.
	}
	
	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		Logevent logevent = new Logevent("SESSION_EXPIRED");
		logevent.setSessionId(event.getSession().getId());
		logeventService.create(logevent);
		
		super.sessionDestroyed(event);
		
	}

}