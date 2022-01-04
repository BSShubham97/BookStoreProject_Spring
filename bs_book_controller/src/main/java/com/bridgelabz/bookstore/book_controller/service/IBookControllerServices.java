package com.bridgelabz.bookstore.book_controller.service;

import java.util.List;

import com.bridgelabz.bookstore.book_controller.dto.BookDto;
import com.bridgelabz.bookstore.book_controller.model.BookModel;



public interface IBookControllerServices {

	BookModel createBook(String token ,BookDto bookDto);
	List<BookModel> getAllBooks();
    BookModel getBookById(String token, Long id);
	BookModel updateBook(String token,Long id, BookDto hiredCandidateDto);
	BookModel deleteBook(String token, Long id);
	String deleteAllData(String token);
	BookModel changeBookQuantity(String token , long id , int quanity);
	BookModel changeBookPrice(String token , long id, int price );
}
