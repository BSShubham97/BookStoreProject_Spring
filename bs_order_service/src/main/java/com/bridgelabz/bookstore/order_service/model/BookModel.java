package com.bridgelabz.bookstore.order_service.model;

import lombok.Data;

public @Data class BookModel {

	private Long book_Id;
	private String bookName;
	private String bookAuthor;
	private String bookDescription;
	// Book Logo-MultiPart
	private Integer bookPrice;
	private Integer bookQuantity;

	public BookModel() {
		super();
	}

}
