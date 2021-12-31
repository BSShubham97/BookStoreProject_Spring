package com.bridgelabz.bookstore.user_registration.service;

import java.util.List;

import javax.validation.Valid;

import com.bridgelabz.bookstore.user_registration.dto.ForgotPassDTO;
import com.bridgelabz.bookstore.user_registration.dto.LoginDto;
import com.bridgelabz.bookstore.user_registration.dto.UserDto;
import com.bridgelabz.bookstore.user_registration.model.UserModel;

public interface IUserRegistrationService {

	List<UserModel> getAllUsers();

	UserModel createUser(UserDto userDto);

	UserModel updateUser(String token, UserDto userDto);

	UserModel getUserById(long id);

	UserModel deleteUser(long id);

	UserModel loginRequest(@Valid LoginDto loginDto);

	UserModel reset(@Valid String password, String token);

	UserModel forgetPassword(ForgotPassDTO forgotPassDTO);

	Boolean verifyUser(String token);

}
