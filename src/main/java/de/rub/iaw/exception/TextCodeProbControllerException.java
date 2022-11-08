package de.rub.iaw.exception;

/**
 * Exception that is used when an exception occurs in the TextCodeProbController class
 * @author Sarah Böning
 **/

public class TextCodeProbControllerException extends RuntimeException {

	private static final long serialVersionUID = -3269974912469372302L;

	public TextCodeProbControllerException(String msg) {
		super(msg);
	}

}
