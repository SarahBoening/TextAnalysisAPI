package de.rub.iaw.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AlreadyExistException extends RuntimeException {

	private static final long serialVersionUID = -5035221874577234723L;

	public AlreadyExistException(String msg) {
		super(msg);
	}
}
