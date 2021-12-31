package com.bridgelabz.bookstore.user_registration.util;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class Email {

	String to;
	String from;
	String subject;
	String body;

}
