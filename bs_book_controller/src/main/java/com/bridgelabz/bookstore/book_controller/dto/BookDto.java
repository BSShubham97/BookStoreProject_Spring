package com.bridgelabz.bookstore.book_controller.dto;

import lombok.Data;

public @Data class BookDto {
	private Long book_Id;
	private String bookName;
	private String bookAuthor;
	private String bookDescription;
    //Book Logo-MultiPart
	private Integer bookPrice;
    private Integer bookQuantity;
}
