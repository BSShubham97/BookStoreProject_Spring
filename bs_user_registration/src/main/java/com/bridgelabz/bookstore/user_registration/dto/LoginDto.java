package com.bridgelabz.bookstore.user_registration.dto;

import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class LoginDto {
	@Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")
	private String email;
	private String password;
}
