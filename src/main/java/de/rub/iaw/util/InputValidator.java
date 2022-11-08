package de.rub.iaw.util;

public class InputValidator {

	/**
	 * Checks if a String is not null, empty or full of blanks
	 * @param input
	 * @return true if valid, false if null or empty or full of blanks
	 */
	public static Boolean ValidateString(String input) {
		
		final String pattern = "^\\s*$";
		
		if(input == null || input.matches(pattern)) { 
			return false;
		}
		
		return true;
	}
	
	/**
	 * Checks if String has values and is a valid email
	 * @param email
	 * @return true for valid email, false for null, empty, blank, invalid email address
	 */
	public static Boolean ValidateEmail(String email) {
		
		// check if fields are empty
		if(!InputValidator.ValidateString(email)) {
			return false;
		}

		final String pattern = "\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		if(!email.matches(pattern)) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Checks if a password is given and longer than 6 characters
	 * @param password
	 * @return true if password is given and longer than 6 characters
	 */
	public static Boolean ValidatePassword(String password) {
		
		// check if fields are empty
		if(!InputValidator.ValidateString(password)) {
			return false;
		}
		
		// simple length check
		if(password.length() < 6) {
			return false;
		}
		
		return true;
	}
	
}
