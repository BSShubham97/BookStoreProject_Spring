package com.bridgelabz.bookstore.cart_service.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstore.cart_service.dto.CartDto;
import com.bridgelabz.bookstore.cart_service.model.CartModel;
import com.bridgelabz.bookstore.cart_service.repository.CartRepository;
import com.bridgelabz.bookstore.cart_service.util.TokenUtil;

@Service
public class CartService implements ICartService {
	@Autowired
	TokenUtil tokenUtil;
	@Autowired
	CartRepository cartRepository;

	@Override
	public CartModel addToCart(String token, CartDto cartDto) {
		CartModel cart = new CartModel(cartDto);
		cart.setUser_Id(tokenUtil.decodeToken(token));
		return cartRepository.save(cart);

	}

	@Override
	public List<CartModel> getAllCartItem(String token) {
		List<CartModel> cartList = new ArrayList<CartModel>();
		cartRepository.findAll().forEach(cartList::add);
		return cartList;

	}

	@Override
	public CartModel getCartItemsById(String token) {
		Long id = tokenUtil.decodeToken(token);
		return cartRepository.findById(id).get();
	}

	@Override
	public CartModel updateCartItem(String token, Long id, CartDto cartDto) {
		Optional<CartModel> cartData = cartRepository.findById(id);
		if (cartData.isPresent()) {
			cartData.get().setBook_Id(cartDto.getBook_Id());
			cartData.get().setQuantity(cartDto.getQuantity());
			cartRepository.save(cartData.get());
			return cartData.get();
		}
		return null;
	}

	@Override
	public void removeCartItem(String token, Long id) {
		Optional<CartModel> cartData = cartRepository.findById(id);
		if (cartData.isPresent()) {
			cartRepository.deleteById(id);
		}

	}

}
