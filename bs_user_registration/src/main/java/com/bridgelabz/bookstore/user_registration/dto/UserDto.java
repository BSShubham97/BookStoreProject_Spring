package com.bridgelabz.bookstore.user_registration.dto;


import java.util.Date;

import lombok.Data;

@Data
public class UserDto {

	private Long id;
	private String firstName;
	private String lastName;
	private String kyc;
	private String dob;
	private Date registeredDate;
	private Date updatedDate;
	private String password;
	private String email;
	private Boolean verify;
	private String otp;
	private Date purchaseDate;
	private Date expiryDate;
}
