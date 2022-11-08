package de.rub.iaw.exception;

/**
 * Exception that is used when the LIWC API does not respond
 * @author Sarah Böning
 **/

public class LiwcApiException extends RuntimeException{

	private static final long serialVersionUID = -4306700627864801213L;

	public LiwcApiException(String msg){
		super(msg);
	}
}
