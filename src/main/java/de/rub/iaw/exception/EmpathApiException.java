package de.rub.iaw.exception;

/**
 * Exception that is used when an exception in the EmpathApiHandler class occurs
 * @author Sarah Böning
 **/

public class EmpathApiException extends RuntimeException{

	private static final long serialVersionUID = -8922785814440309178L;

	public EmpathApiException(String msg){
		super(msg);
	}
}
