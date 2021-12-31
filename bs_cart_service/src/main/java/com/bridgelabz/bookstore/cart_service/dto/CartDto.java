package com.bridgelabz.bookstore.cart_service.dto;

import lombok.Data;

@Data
public class CartDto {
	
		private Long cart_Id;
		private Long user_Id;
		private Long book_Id;
		private Long quantity;

}
