package com.bridgelabz.bookstore.user_registration.exception;

public class LoginException extends Exception {
	private static final long serialVersionUID = 1L;
	private String message;

	public LoginException(String message) {
		this.message = message;
	}

	public LoginException() {
	}

	public String getMessage() {
		return message;
	}


}
