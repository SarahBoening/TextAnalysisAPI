package de.rub.iaw.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadParameterException extends RuntimeException {

	private static final long serialVersionUID = 4753661652022146131L;
	
	public BadParameterException(String msg) {
		super(msg);
	}

}
