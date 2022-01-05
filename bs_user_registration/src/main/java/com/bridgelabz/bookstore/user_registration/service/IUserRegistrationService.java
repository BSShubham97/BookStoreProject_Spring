package com.bridgelabz.bookstore.user_registration.service;

import java.util.List;

import javax.validation.Valid;

import com.bridgelabz.bookstore.user_registration.dto.ForgotPassDTO;
import com.bridgelabz.bookstore.user_registration.dto.LoginDto;
import com.bridgelabz.bookstore.user_registration.dto.UserDto;
import com.bridgelabz.bookstore.user_registration.exception.LoginException;
import com.bridgelabz.bookstore.user_registration.exception.UserNotFoundException;
import com.bridgelabz.bookstore.user_registration.model.UserModel;

public interface IUserRegistrationService {

	List<UserModel> getAllUsers();

	UserModel createUser(UserDto userDto) throws LoginException;

	UserModel updateUser(String token, UserDto userDto) throws LoginException ,UserNotFoundException;

	UserModel getUserById(long id) throws LoginException ,UserNotFoundException;

	UserModel deleteUser(long id) throws LoginException ,UserNotFoundException;

	UserModel loginRequest(@Valid LoginDto loginDto)throws LoginException, UserNotFoundException;

	UserModel reset(@Valid String password, String token);

	UserModel forgetPassword(ForgotPassDTO forgotPassDTO) throws LoginException;

	Boolean verifyUser(String token);

	UserModel sendOtp(String token);

	Boolean checkOtp(String token, Integer otp);

	UserModel purchase(String token) throws UserNotFoundException;
	
	UserModel expiry(String token) throws UserNotFoundException;

}
