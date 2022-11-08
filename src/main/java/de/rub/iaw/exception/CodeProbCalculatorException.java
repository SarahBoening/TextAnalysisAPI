package de.rub.iaw.exception;

/**
 * Exception that is used when an exception in the CodeProbCalculator class occurs
 * @author Sarah Böning
 **/

public class CodeProbCalculatorException extends RuntimeException {

	private static final long serialVersionUID = -7289460130824547659L;
	public CodeProbCalculatorException(String msg) {
		super(msg);
	}
}
