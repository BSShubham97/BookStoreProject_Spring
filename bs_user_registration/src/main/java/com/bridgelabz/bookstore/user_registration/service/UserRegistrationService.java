package com.bridgelabz.bookstore.user_registration.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstore.user_registration.dto.ForgotPassDTO;
import com.bridgelabz.bookstore.user_registration.dto.LoginDto;
import com.bridgelabz.bookstore.user_registration.dto.UserDto;
import com.bridgelabz.bookstore.user_registration.exception.LoginException;
import com.bridgelabz.bookstore.user_registration.exception.UserNotFoundException;
import com.bridgelabz.bookstore.user_registration.model.UserModel;
import com.bridgelabz.bookstore.user_registration.repository.UserRepository;
import com.bridgelabz.bookstore.user_registration.util.Email;
import com.bridgelabz.bookstore.user_registration.util.TokenUtil;

@Service
public class UserRegistrationService implements IUserRegistrationService {

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

	public UserModel getUserById(long id) throws UserNotFoundException {
		Optional<UserModel> userData = userRepository.findById(id);
		if (userData.isPresent()) {
			return userRepository.findById(id).get();
		} else
			throw new UserNotFoundException("No such user Present !!!");

	}

	public UserModel createUser(@Valid UserDto userDto) throws LoginException {
		Optional<UserModel> userCheck = userRepository.findByEmail(userDto.getEmail());
		if (userCheck.isPresent()) {
			throw new LoginException("Email already exists");
		} else {
			UserModel userData = new UserModel(userDto);
			userData.setPassword(passwordEncoder.encode(userDto.getPassword()));
			userData.setRegisteredDate(LocalDate.now());
			userData.setExpiryDate(LocalDate.now().plusYears(1));

			return userRepository.save(userData);
		}
	}

	public UserModel updateUser(String token, UserDto userDto) throws UserNotFoundException {
		Long Id = tokenUtil.decodeToken(token);
		Optional<UserModel> userData = userRepository.findById(Id);
		if (userData.isPresent()) {
			userData.get().setFirstName(userDto.getFirstName());
			userData.get().setLastName(userDto.getLastName());
			userData.get().setEmail(userDto.getEmail());
			userData.get().setDob(userDto.getDob());
			userData.get().setUpdatedDate(LocalDate.now());
			userRepository.save(userData.get());
			return userData.get();
		} else {
			throw new UserNotFoundException("User not present of this Id");

		}
	}

	public UserModel deleteUser(long id) throws UserNotFoundException {
		Optional<UserModel> userData = userRepository.findById(id);
		if (userData.isPresent()) {
			userRepository.delete(userData.get());
			;
		} else {
			throw new UserNotFoundException("User not present of this Id");
		}
		return null;
	}

	public UserModel loginRequest(@Valid LoginDto loginRequest) throws UserNotFoundException {
		Optional<UserModel> email = userRepository.findByEmail(loginRequest.getEmail());
		if (email.isPresent()) {
			System.out.println(email.get().toString());
		} else {
			throw new UserNotFoundException("User not found");
		}
		if (passwordEncoder.matches(loginRequest.getPassword(), email.get().getPassword())) {
			return email.get();
		}
		return null;
	}

	public UserModel forgetPassword(ForgotPassDTO forgotPassDTO) throws LoginException {
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

		} else {
			throw new LoginException("Email not found !!!");
		}
	}

	public UserModel reset(@Valid String password, String token) {
		// DECODING TOKEN
		Long id = tokenUtil.decodeToken(token);
		// CHECKING USER PRESENT OR NOT
		Optional<UserModel> userData = userRepository.findById(id);
		if (userData.isPresent()) {
			// SETTING THE NEW PASSWORD AND SAVING INTO THE DATABASE
			userData.get().setPassword(passwordEncoder.encode(password));

			userData.get().setUpdatedDate(LocalDate.now());
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

	@Override
	public UserModel sendOtp(String token) {
		Long id = tokenUtil.decodeToken(token);

		Optional<UserModel> userData = userRepository.findById(id);
		if (userData.isPresent()) {
			Random random = new Random();
			Integer otpRandom = random.nextInt(10000);
			userData.get().setOtp(otpRandom);
			String userEmailId = userData.get().getEmail();
			email.setTo(userEmailId);
			email.setFrom("shubhamb97.it@gmail.com");
			email.setSubject("\033[0;1m" + "VERIFICATION OTP");
			email.setBody("Please keep this OTP for verification");
			email.setBody("Your OTP: " + otpRandom);
			userData.get().setOtp(otpRandom);
		}
		return null;
	}

	@Override
	public Boolean checkOtp(String token, Integer otp) {
		Long id = tokenUtil.decodeToken(token);
		Optional<UserModel> userData = userRepository.findById(id);
		if (userData.isPresent()) {
			if (userData.get().getOtp() == otp) {
				return true;
			} else {
				return false;
			}
		}
		return null;
	}

	@Override
	public UserModel purchase(String token) throws UserNotFoundException {
		long Id = tokenUtil.decodeToken(token);
		Optional<UserModel> isUserPresent = userRepository.findById(Id);
		if (isUserPresent.isPresent()) {
			LocalDate todayDate = LocalDate.now();
			isUserPresent.get().setPurchaseDate(todayDate);
			isUserPresent.get().setExpiryDate(todayDate.plusYears(1));
			userRepository.save(isUserPresent.get());

			String userEmailId = isUserPresent.get().getEmail();
			email.setTo(userEmailId);
			email.setFrom("shubhamb97.it@gmail.com");
			email.setSubject("Purchase successfull !!!");
			email.setBody("Your subscription will end on " + isUserPresent.get().getExpiryDate());
		} else {
			throw new UserNotFoundException("User not Present !!");
		}
		return null;
	}

	@Override
	public UserModel expiry(String token) throws UserNotFoundException {
		long Id = tokenUtil.decodeToken(token);
		Optional<UserModel> isUserPresent = userRepository.findById(Id);
		if (isUserPresent.isPresent()) {
			LocalDate todayDate = LocalDate.now();
			String userEmailId = isUserPresent.get().getEmail();
			if (todayDate.equals(isUserPresent.get().getExpiryDate())) {
				email.setTo(userEmailId);
				email.setFrom("shubhamb97.it@gmail.com");
				email.setSubject("Remainder of Service Expiry !!!");
				email.setBody("Your subscription is about to end please renew your subscription.");

			}
		} else {
			throw new UserNotFoundException("User Not Found !!!");

		}
		return null;
	}
}
