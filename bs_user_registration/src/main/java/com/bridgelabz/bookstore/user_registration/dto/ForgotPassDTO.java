package com.bridgelabz.bookstore.user_registration.dto;

import javax.validation.constraints.Pattern;

import lombok.Data;

@Data  
public class ForgotPassDTO {
	@Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")
	String email;

}
