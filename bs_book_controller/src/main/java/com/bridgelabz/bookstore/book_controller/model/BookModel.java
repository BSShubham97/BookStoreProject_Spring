package com.bridgelabz.bookstore.book_controller.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



import com.bridgelabz.bookstore.book_controller.dto.BookDto;

import lombok.Data;

@Entity 
@Table(name="Book_Info")
public @Data class BookModel {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO) 
	private Long book_Id;
	private String bookName;
	private String bookAuthor;
	private String bookDescription;
    //Book Logo-MultiPart
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
