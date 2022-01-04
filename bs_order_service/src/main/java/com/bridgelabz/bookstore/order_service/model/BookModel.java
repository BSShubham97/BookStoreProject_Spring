package com.bridgelabz.bookstore.order_service.model;

import com.bridgelabz.bookstore.order_service.dto.BookDto;

import lombok.Data;

public @Data class BookModel {

	private Long book_Id;
	private String bookName;
	private String bookAuthor;
	private String bookDescription;
	// Book Logo-MultiPart
	private Integer bookPrice;
	private Integer bookQuantity;

	public BookModel(BookDto bookDto) {
		super();
		this.book_Id = bookDto.getBook_Id();
		this.bookName = bookDto.getBookName();
		this.bookAuthor = bookDto.getBookAuthor();
		this.bookDescription = bookDto.getBookDescription();
		this.bookPrice = bookDto.getBookPrice();
		this.bookQuantity = bookDto.getBookQuantity();
	}

	public BookModel() {
		super();
	}

}
