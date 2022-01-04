package com.bridgelabz.bookstore.user_registration.dto;


import java.time.LocalDate;
import java.util.Date;

import lombok.Data;

@Data
public class UserDto {

	private Long id;
	private String firstName;
	private String lastName;
	private String kyc;
	private String dob;
	private LocalDate registeredDate;
	private LocalDate updatedDate;
	private String password;
	private String email;
	private Boolean verify;
	private Integer otp;
	private LocalDate purchaseDate;
	private LocalDate expiryDate;
}
