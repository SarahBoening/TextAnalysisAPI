package de.rub.iaw.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntryDoesNotExistException extends RuntimeException {

	private static final long serialVersionUID = -7014057456951887123L;
	
	public EntryDoesNotExistException(String msg) {
		super(msg);
	}
}
