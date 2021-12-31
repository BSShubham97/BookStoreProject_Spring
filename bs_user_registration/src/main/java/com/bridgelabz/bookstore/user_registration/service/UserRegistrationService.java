package com.bridgelabz.bookstore.user_registration.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstore.user_registration.dto.ForgotPassDTO;
import com.bridgelabz.bookstore.user_registration.dto.LoginDto;
import com.bridgelabz.bookstore.user_registration.dto.UserDto;
import com.bridgelabz.bookstore.user_registration.model.UserModel;
import com.bridgelabz.bookstore.user_registration.repository.UserRepository;
import com.bridgelabz.bookstore.user_registration.util.Email;
import com.bridgelabz.bookstore.user_registration.util.TokenUtil;

@Service
public class UserRegistrationService implements IUserRegistrationService{

	@Autowired
	TokenUtil tokenUtil;
	@Autowired
	UserRepository userRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	Email email;
	@Autowired
	EmailSenderService mailService;

	public List<UserModel> getAllUsers() {
		List<UserModel> userDataList = new ArrayList<UserModel>();
		userRepository.findAll().forEach(userDataList::add);
		return userDataList;

	}

	public UserModel getUserById(long id) {
		return userRepository.findById(id).get();
	}

	public UserModel createUser(@Valid UserDto userDto) {
		UserModel userData = new UserModel(userDto);
		userData.setPassword(passwordEncoder.encode(userDto.getPassword()));
		userData.setRegisteredDate(new Date(System.currentTimeMillis()));
		return userRepository.save(userData);
	}

	public UserModel updateUser(String token, UserDto userDto) {
		Long Id = tokenUtil.decodeToken(token);
		Optional<UserModel> userData = userRepository.findById(Id);
		if (userData.isPresent()) {
			userData.get().setFirstName(userDto.getFirstName());
			userData.get().setLastName(userDto.getLastName());
			userData.get().setEmail(userDto.getEmail());
			userData.get().setDob(userDto.getDob());
			userData.get().setUpdatedDate(new Date(System.currentTimeMillis()));
			userRepository.save(userData.get());
			return userData.get();
		}
		return null;
	}

	public UserModel deleteUser(long id) {
		Optional<UserModel> userData = userRepository.findById(id);
		if (userData.isPresent()) {
			userRepository.delete(userData.get());
			;
		}
		return null;
	}

	public UserModel loginRequest(@Valid LoginDto loginRequest) {
		Optional<UserModel> email = userRepository.findByEmail(loginRequest.getEmail());
		if (email.isPresent()) {
			System.out.println(email.get().toString());
			if (passwordEncoder.matches(loginRequest.getPassword(), email.get().getPassword())) {
				return email.get();
			}
		}
		return null;
	}

	public UserModel forgetPassword(ForgotPassDTO forgotPassDTO) {
		String emailId = forgotPassDTO.getEmail();
		Optional<UserModel> isPresent = userRepository.findByEmail(emailId);
		if (isPresent.isPresent()) {
			email.setTo(emailId);
			email.setFrom("shubhamb97.it@gmail.com");
			email.setSubject("forgot password link");
			email.setBody("This a demo email");
			email.setBody(mailService.getLink("Hii  " + isPresent.get().getFirstName() + " Reset your password -"
					+ " http://localhost:8080/reset/", isPresent.get().getId()));
			mailService.sendMail(email.getTo(), email.getSubject(), email.getBody());

			return isPresent.get();

		}
		return null;
	}

	public UserModel reset(@Valid String password, String token) {
		// DECODING TOKEN
		Long id = tokenUtil.decodeToken(token);
		// CHECKING USER PRESENT OR NOT
		Optional<UserModel> userData = userRepository.findById(id);
		if (userData.isPresent()) {
			// SETTING THE NEW PASSWORD AND SAVING INTO THE DATABASE
			userData.get().setPassword(passwordEncoder.encode(password));

			userData.get().setUpdatedDate(new Date(System.currentTimeMillis()));
			return userRepository.save(userData.get());
		}
		return null;
	}

	public Boolean verifyUser(String token) {
		Long decodedToken = tokenUtil.decodeToken(token);
		if ((userRepository.existsById(decodedToken)) == true) {
			return true;
		} else {
			return false;
		}

	}

}
