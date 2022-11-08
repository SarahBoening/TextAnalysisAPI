package de.rub.iaw.exception;

/**
 * Exception used when content analysis API sends a Bad Request status code
 * @author Sarah Böning
 **/

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

	private static final long serialVersionUID = 6863384149554409566L;

	public BadRequestException(String msg) {
		super(msg);
	}

}
