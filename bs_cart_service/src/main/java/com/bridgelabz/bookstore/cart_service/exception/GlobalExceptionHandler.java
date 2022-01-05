package com.bridgelabz.bookstore.cart_service.exception;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bridgelabz.bookstore.cart_service.dto.ResponseDto;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final String message = "Exception While Processing REST Request";

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ResponseDto> handelHttpMessageNotReadableException(
			HttpMessageNotReadableException exception) {
		ResponseDto responseDTO = new ResponseDto(message, "Should have date in dd MM yyyy format");
		return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ResponseDto> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException exception) {
		List<ObjectError> errorList = exception.getBindingResult().getAllErrors();
		List<String> errorMessage = errorList.stream().map(objerr -> objerr.getDefaultMessage())
				.collect(Collectors.toList());

		ResponseDto responseDTO = new ResponseDto(message, errorMessage);
		return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<ResponseDto> handlenosuchelementexception(NoSuchElementException nosuchelement )
	{
		ResponseDto responseDto = new ResponseDto(message , "Element not available !!!");
		return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(CartException.class)
	public ResponseEntity<ResponseDto> handleUserException(CartException exception) {
		ResponseDto responseDTO = new ResponseDto(message, exception.getMessage());
		return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.BAD_REQUEST);
	}
}
