package com.bridgelabz.bookstore.order_service.exceptions;

@SuppressWarnings("serial")
public class OrderException extends RuntimeException {

	public OrderException(String message) {
		super(message);
	}
}
