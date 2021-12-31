package com.bridgelabz.bookstore.user_registration.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.bookstore.user_registration.dto.ForgotPassDTO;
import com.bridgelabz.bookstore.user_registration.dto.LoginDto;
import com.bridgelabz.bookstore.user_registration.dto.ResponseDto;
import com.bridgelabz.bookstore.user_registration.dto.UserDto;
import com.bridgelabz.bookstore.user_registration.exception.LoginException;
import com.bridgelabz.bookstore.user_registration.model.UserModel;
import com.bridgelabz.bookstore.user_registration.repository.UserRepository;
import com.bridgelabz.bookstore.user_registration.service.EmailSenderService;
import com.bridgelabz.bookstore.user_registration.service.IUserRegistrationService;
import com.bridgelabz.bookstore.user_registration.util.TokenUtil;

@RequestMapping("/bookstoreuser")
@RestController
public class RegistrationController {

	@Autowired
	IUserRegistrationService iuserRegistration;
	@Autowired
	TokenUtil tokenUtil;
	@Autowired(required = true)
	UserRepository useRepository;
	@Autowired
	EmailSenderService mailService;
	
	
	
	@RequestMapping(value = { "", "/", "get" })
	public ResponseEntity<ResponseDto> getUserRegisterData() {
		List<UserModel> userDataList = iuserRegistration.getAllUsers();
		ResponseDto respDto = new ResponseDto("GET CALL FOR USERS SUCCESSFUL", userDataList);
		return new ResponseEntity<ResponseDto>(respDto, HttpStatus.OK);
	}

	@RequestMapping("/get/{userId}")
	public ResponseEntity<ResponseDto> getById(@PathVariable("userId") int userId) {
		UserModel userDataList = iuserRegistration.getUserById(userId);
		ResponseDto respDto = new ResponseDto("GET CALL FOR USER SUCCESSFUL", userDataList);
		return new ResponseEntity<ResponseDto>(respDto, HttpStatus.OK);
	}

	@PostMapping("/register")
	public ResponseEntity<ResponseDto> createUser(@Valid @RequestBody UserDto userDto) {
		
		UserModel userData = null;
		userData = iuserRegistration.createUser(userDto);
		String registerToken = tokenUtil.createToken(userData.getId());
		ResponseDto respDto = new ResponseDto("User Registered Succcessfully !!!",
				                               registerToken);
		mailService.sendMail(userDto.getEmail() ,
				"This is the Registration Message !!! ",
				                       "You have been registered . Your token is: " +registerToken
				                       );
		
		return new ResponseEntity<ResponseDto>(respDto, HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateEmployeePayrollData(@RequestHeader(name="token") String token,
			@Valid @RequestBody UserDto userDto) {
		
		UserModel userData = null;
		userData = iuserRegistration.updateUser(token , userDto);
		ResponseDto respDto = new ResponseDto("UPDATED USER DATA SUCCESSFULLY !!! ",
				                                tokenUtil.createToken(userData.getId()));
		return new ResponseEntity<ResponseDto>(respDto, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<ResponseDto> deleteUser(@PathVariable("userId") int userId) {
		iuserRegistration.deleteUser(userId);
		ResponseDto respDto = new ResponseDto("DELETED USER DATA SUCCESSFULLY !!!", "DATABASE UPDATED.");
		return new ResponseEntity<ResponseDto>(respDto, HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<ResponseDto> loginUser(@RequestBody LoginDto loginDto) {
		UserModel userData = null;
		userData =iuserRegistration.loginRequest(loginDto);
		ResponseDto respDto = new ResponseDto("LOGIN SUCCESSFULLY !!!", tokenUtil.createToken(userData.getId()));
		return new ResponseEntity<ResponseDto>(respDto, HttpStatus.OK);

	}
	
	@GetMapping("/forgotpassword")
	public ResponseEntity<ResponseDto> ForgetPassword(@Valid @RequestBody ForgotPassDTO forgotPassDTO)
			 {
		UserModel userData = iuserRegistration.forgetPassword(forgotPassDTO);
		ResponseDto respDto = new ResponseDto("Forgot Password Successfull", userData);
		return new ResponseEntity<ResponseDto>(respDto, HttpStatus.OK);
	}

	@PostMapping("/reset/{token}")
	ResponseEntity<ResponseDto> resetpass(@Valid @RequestParam String password, @PathVariable String token) {
		UserModel userData = iuserRegistration.reset(password, token);
		ResponseDto respDto = new ResponseDto(" Password Reset Successfully", userData);
		return new ResponseEntity<ResponseDto>(respDto, HttpStatus.OK);
	}


	@GetMapping("/verify")
	ResponseEntity<Boolean> verifyUser( @RequestParam(name="token") String token) throws LoginException{
		
	if(token != null) {
		Boolean isTrue = iuserRegistration.verifyUser(token);
		return new ResponseEntity<Boolean>(isTrue, HttpStatus.OK);
	
	}else
		throw new LoginException("Login Token not valid ");
}
}
