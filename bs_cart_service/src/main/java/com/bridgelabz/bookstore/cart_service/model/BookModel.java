package com.bridgelabz.bookstore.cart_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public @Data class BookModel {
	
	private Long book_Id;
	private String bookName;
	private String bookAuthor;
	private String bookDescription;
    //Book Logo-MultiPart
	private Integer bookPrice;
    private Integer bookQuantity;
    
	

    
}
