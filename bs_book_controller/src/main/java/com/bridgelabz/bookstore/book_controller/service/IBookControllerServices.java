package com.bridgelabz.bookstore.book_controller.service;

import java.util.List;

import com.bridgelabz.bookstore.book_controller.dto.BookDto;
import com.bridgelabz.bookstore.book_controller.model.BookModel;



public interface IBookControllerServices {

	BookModel createBook(BookDto bookDto);
	List<BookModel> getAllBooks();
    BookModel getBookById(Long id);
	BookModel updateBook(String token, BookDto hiredCandidateDto);
	BookModel deleteBook(Long id);
	String deleteAllData();
}
