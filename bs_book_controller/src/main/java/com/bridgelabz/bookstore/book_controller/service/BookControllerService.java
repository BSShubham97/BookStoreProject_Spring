package com.bridgelabz.bookstore.book_controller.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bridgelabz.bookstore.book_controller.dto.BookDto;
import com.bridgelabz.bookstore.book_controller.exception.BookControllerException;
import com.bridgelabz.bookstore.book_controller.model.BookModel;
import com.bridgelabz.bookstore.book_controller.repository.BookRepository;
import com.bridgelabz.bookstore.book_controller.util.JwtTokenUtil;

@Service
public class BookControllerService implements IBookControllerServices {

	@Autowired
	BookRepository bookRepository;

	@Autowired
	JwtTokenUtil tokenUtil;
	@Autowired
	RestTemplate restTemplate;

	@Override
	public List<BookModel> getAllBooks() {
		List<BookModel> bookData = new ArrayList<>();
		bookRepository.findAll().forEach(bookData::add);
		return bookData;
	}

	public BookModel createBook(String token, @Valid BookDto bookDto) {
		boolean verify = restTemplate.getForObject("http://BOOKSTORE-USER/bookstoreuser/verify?token=" + token,
				Boolean.class);
		if (verify == true) {
			BookModel bookData = new BookModel(bookDto);
			return bookRepository.save(bookData);
		} else
			throw new BookControllerException("Access Denied...! please check the login token");
	}

	@Override
	public BookModel getBookById(String token, Long id) {
		boolean verify = restTemplate.getForObject("http://BOOKSTORE-USER/bookstoreuser/verify?token=" + token,
				Boolean.class);
		if (verify == true) {
			return bookRepository.findById(id).get();
		} else
			throw new BookControllerException("Access Denied...! please check the login token");
	}

	@Override
	public BookModel updateBook(String token, Long id, BookDto bookDto) {
		boolean verify = restTemplate.getForObject("http://BOOKSTORE-USER/bookstoreuser/verify?token=" + token,
				Boolean.class);
		if (verify == true) {
			Optional<BookModel> bookData = bookRepository.findById(id);
			if (bookData.isPresent()) {
				bookData.get().setBookName(bookDto.getBookName());
				bookData.get().setBookAuthor(bookDto.getBookAuthor());
				bookData.get().setBookDescription(bookDto.getBookDescription());
				bookData.get().setBookPrice(bookDto.getBookPrice());
				bookData.get().setBookQuantity(bookDto.getBookQuantity());
				bookRepository.save(bookData.get());
				return bookData.get();
			}else 
				throw new BookControllerException("Book is not present");
		} else
			throw new BookControllerException("Access Denied...! please check the login token");
	}

	@Override
	public BookModel deleteBook(String token, Long id) {
		boolean verify = restTemplate.getForObject("http://BOOKSTORE-USER/bookstoreuser/verify?token=" + token,
				Boolean.class);
		if (verify == true) {
			Optional<BookModel> bookData = bookRepository.findById(id);
			if (bookData.isPresent()) {
				bookRepository.delete(bookData.get());
			}else 
				throw new BookControllerException("Book is not present");
		} else
			throw new BookControllerException("Access Denied...! please check the login token");
		return null;
	}

	@Override
	public String deleteAllData(String token) {
		boolean verify = restTemplate.getForObject("http://BOOKSTORE-USER/bookstoreuser/verify?token=" + token,
				Boolean.class);
		if (verify == true) {
			bookRepository.deleteAll();
		} else
			throw new BookControllerException("Access Denied...! please check the login token");
		return null;
	}

	@Override
	public BookModel changeBookQuantity(String token, long id, int quantity) {
		boolean verify = restTemplate.getForObject("http://BOOKSTORE-USER/bookstoreuser/verify?token=" + token,
				Boolean.class);
		if (verify == true) {
			Optional<BookModel> isPresent = bookRepository.findById(id);
			if (isPresent.isPresent()) {
				isPresent.get().setBookQuantity(quantity);
				bookRepository.save(isPresent.get());
			}else 
				throw new BookControllerException("Book is not present");
		} else
			throw new BookControllerException("Access Denied...! please check the login token");
		return null;
	}

	@Override
	public BookModel changeBookPrice(String token, long id, int price) {
		boolean verify = restTemplate.getForObject("http://BOOKSTORE-USER/bookstoreuser/verify?token=" + token,
				Boolean.class);
		if (verify == true) {
			Optional<BookModel> isPresent = bookRepository.findById(id);
			if (isPresent.isPresent()) {
				isPresent.get().setBookPrice(price);
				bookRepository.save(isPresent.get());
			}else 
				throw new BookControllerException("Book is not present");
		} else
			throw new BookControllerException("Access Denied...! please check the login token");
		return null;
	}

}
