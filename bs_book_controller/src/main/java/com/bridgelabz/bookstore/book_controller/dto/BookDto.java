package com.bridgelabz.bookstore.book_controller.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

public @Data class BookDto {
	private Long book_Id;
	@NotEmpty
	private String bookName;
	@NotEmpty
	private String bookAuthor;
	private String bookDescription;
    //Book Logo-MultiPart
	@NotBlank
	private Integer bookPrice;
    @NotBlank
	private Integer bookQuantity;
}
