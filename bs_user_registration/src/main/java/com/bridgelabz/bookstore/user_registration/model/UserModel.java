package com.bridgelabz.bookstore.user_registration.model;





import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bridgelabz.bookstore.user_registration.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Entity
@Table(name="User_Registration")
public class UserModel {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="ID")
	private Long id;
	private String firstName;
	private String lastName;
	private String kyc;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private String dob;
	private LocalDate registeredDate;
	private LocalDate updatedDate;
	private String password;
	private String email;
	private Boolean verify;
	private Integer otp;
	private LocalDate purchaseDate;
	private LocalDate expiryDate;

	public UserModel(UserDto userDto) {
		super();
		this.id = userDto.getId();
		this.firstName = userDto.getFirstName();
		this.lastName = userDto.getLastName();
		this.kyc = userDto.getKyc();
		this.dob = userDto.getDob();
		this.registeredDate = userDto.getRegisteredDate();
		this.updatedDate = userDto.getUpdatedDate();
		this.password = userDto.getPassword();
		this.email = userDto.getEmail();
		this.verify = userDto.getVerify();
    	this.otp = userDto.getOtp();
		this.purchaseDate = userDto.getPurchaseDate();
		this.expiryDate = userDto.getExpiryDate();
	}

	public UserModel() {
		super();
	}


}
