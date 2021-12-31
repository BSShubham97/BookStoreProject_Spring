package com.bridgelabz.bookstore.cart_service.services;

import java.util.List;

import com.bridgelabz.bookstore.cart_service.dto.CartDto;
import com.bridgelabz.bookstore.cart_service.model.CartModel;

public interface ICartService {

	CartModel addToCart(String token, CartDto cartDto);

	List<CartModel> getAllCartItem(String token);

	CartModel getCartItemsById(String token);

	CartModel updateCartItem(String token, Long id, CartDto cartDto);

	void removeCartItem(String token, Long id);

}
