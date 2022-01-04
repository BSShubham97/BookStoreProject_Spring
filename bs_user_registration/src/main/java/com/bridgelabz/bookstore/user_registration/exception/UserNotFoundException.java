package com.bridgelabz.bookstore.user_registration.exception;

public class UserNotFoundException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public UserNotFoundException(String message) {
		super(message);
		this.message = message;
	}

	public UserNotFoundException() {
	}

	public String getMessage() {
		return message;
	}

}
