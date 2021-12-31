package com.bridgelabz.bookstore.cart_service.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bridgelabz.bookstore.cart_service.dto.CartDto;

import lombok.Data;

@Data
@Entity
@Table(name="Cart_Data")
public class CartModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long cart_Id;
	private Long user_Id;
	private Long book_Id;
	private Long quantity;

	public CartModel() {
		// TODO Auto-generated constructor stub
	}

	public CartModel(CartDto cartDto) {
		this.cart_Id = cartDto.getCart_Id();
		this.user_Id = cartDto.getUser_Id();
		this.book_Id = cartDto.getBook_Id();
		this.quantity = cartDto.getQuantity();
	}

}
