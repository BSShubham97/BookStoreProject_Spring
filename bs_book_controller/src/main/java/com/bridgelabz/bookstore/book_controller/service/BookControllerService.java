package com.bridgelabz.bookstore.book_controller.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstore.book_controller.dto.BookDto;
import com.bridgelabz.bookstore.book_controller.model.BookModel;
import com.bridgelabz.bookstore.book_controller.repository.BookRepository;
import com.bridgelabz.bookstore.book_controller.util.JwtTokenUtil;

@Service
public class BookControllerService implements IBookControllerServices {

	@Autowired
	BookRepository bookRepository;

	@Autowired
	JwtTokenUtil tokenUtil;

	@Override
	public List<BookModel> getAllBooks() {
		List<BookModel> bookData = new ArrayList<>();
		bookRepository.findAll().forEach(bookData::add);
		return bookData;
	}

	public BookModel createBook(@Valid BookDto bookDto) {
		BookModel bookData = new BookModel(bookDto);
		return bookRepository.save(bookData);
	}

	@Override
	public BookModel getBookById(Long id) {
		return bookRepository.findById(id).get();

	}

	@Override
	public BookModel updateBook(String token, BookDto bookDto) {
		Long Id = tokenUtil.decodeToken(token);
		Optional<BookModel> bookData = bookRepository.findById(Id);
		if (bookData.isPresent()) {
			bookData.get().setBook_Id(bookDto.getBook_Id());
			bookData.get().setBookName(bookDto.getBookName());
			bookData.get().setBookAuthor(bookDto.getBookAuthor());
			bookData.get().setBookDescription(bookDto.getBookDescription());
			bookData.get().setBookPrice(bookDto.getBookPrice());
			bookData.get().setBookQuantity(bookDto.getBookQuantity());
			bookRepository.save(bookData.get());
			return bookData.get();
		}
		return null;
	}

	@Override
	public BookModel deleteBook(Long id) {
		Optional<BookModel> bookData = bookRepository.findById(id);
		if (bookData.isPresent()) {
			bookRepository.delete(bookData.get());
			;
		}
		return null;
	}

	@Override
	public String deleteAllData() {
		bookRepository.deleteAll();
		return null;
	}

}
