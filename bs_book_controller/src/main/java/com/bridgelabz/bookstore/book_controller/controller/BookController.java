package com.bridgelabz.bookstore.book_controller.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.bridgelabz.bookstore.book_controller.dto.BookDto;
import com.bridgelabz.bookstore.book_controller.dto.ResponseDto;
import com.bridgelabz.bookstore.book_controller.model.BookModel;
import com.bridgelabz.bookstore.book_controller.repository.BookRepository;
import com.bridgelabz.bookstore.book_controller.service.IBookControllerServices;
import com.bridgelabz.bookstore.book_controller.util.JwtTokenUtil;

@RestController
@RequestMapping("/bookstore")
public class BookController {

	@Autowired
	BookRepository bookRepository;
	@Autowired
	IBookControllerServices bookService;
	@Autowired
	JwtTokenUtil tokenUtil;

	@Autowired
	RestTemplate restTemplate;

	@RequestMapping("/get")
	public ResponseEntity<ResponseDto> getAllBooks(@RequestParam(name = "token") String token) {
		Boolean isVerified = restTemplate.getForObject("http://localhost:8081/lmsuser/verify?token=" + token,
				Boolean.class);
		if (isVerified == true) {
			List<BookModel> bookDataList = bookService.getAllBooks();
			ResponseDto respDto = new ResponseDto("GET CALL FOR BOOKS SUCCESSFUL", bookDataList);
			return new ResponseEntity<ResponseDto>(respDto, HttpStatus.OK);
		} else {
			ResponseDto respDto = new ResponseDto("GET CALL FOR BOOKS UNSUCCESSFUL !!!");
			return new ResponseEntity<ResponseDto>(respDto, HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/addbook")
	public ResponseEntity<ResponseDto> createUser(@Valid @RequestBody BookDto bookDto) {
		BookModel bookModel = bookService.createBook(bookDto);
		ResponseDto respDto = new ResponseDto("BOOK Added Succcessfully !!!",
				tokenUtil.createToken(bookModel.getBook_Id()));
		return new ResponseEntity<ResponseDto>(respDto, HttpStatus.OK);
	}

	@RequestMapping("/get/{id}")
	public ResponseEntity<ResponseDto> getById(@PathVariable("id") long id) {
		BookModel bookDataList = bookService.getBookById(id);
		ResponseDto respDto = new ResponseDto("GET CALL FOR BOOK No." + id + " SUCCESSFUL", bookDataList);
		return new ResponseEntity<ResponseDto>(respDto, HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateData(@RequestHeader(name = "token") String token,
			@Valid @RequestBody BookDto bookDto) {

		BookModel hiredCandidate = bookService.updateBook(token, bookDto);
		ResponseDto respDto = new ResponseDto("UPDATED CANDIDATE DATA SUCCESSFULLY !!! ",
				tokenUtil.createToken(hiredCandidate.getBook_Id()));
		return new ResponseEntity<ResponseDto>(respDto, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ResponseDto> deleteCandidate(@PathVariable("id") long id) {
		bookService.deleteBook(id);
		ResponseDto respDto = new ResponseDto("DELETED BOOK DATA SUCCESSFULLY !!!", "DATABASE UPDATED.");
		return new ResponseEntity<ResponseDto>(respDto, HttpStatus.OK);
	}

	@DeleteMapping("/cleardata")
	public ResponseEntity<ResponseDto> deleteAllData() {
		bookService.deleteAllData();
		ResponseDto respDto = new ResponseDto("DELETED EVERYTHING SUCCESSFULLY !!!", "DATABASE UPDATED. NOW EMPTY ");
		return new ResponseEntity<ResponseDto>(respDto, HttpStatus.OK);
	}

}
