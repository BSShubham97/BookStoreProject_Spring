package com.bridgelabz.bookstore.cart_service.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bridgelabz.bookstore.cart_service.dto.CartDto;
import com.bridgelabz.bookstore.cart_service.exception.CartException;
import com.bridgelabz.bookstore.cart_service.model.BookModel;
import com.bridgelabz.bookstore.cart_service.model.CartModel;
import com.bridgelabz.bookstore.cart_service.repository.CartRepository;
import com.bridgelabz.bookstore.cart_service.util.TokenUtil;

@Service
public class CartService implements ICartService {
	@Autowired
	TokenUtil tokenUtil;
	@Autowired
	CartRepository cartRepository;
	@Autowired
	RestTemplate restTemplate;

	@Override
	public CartModel addToCart(String token, CartDto cartDto) {
		boolean verify = restTemplate.getForObject("http://BOOKSTORE-USER/bookstoreuser/verify?token=" + token,
				Boolean.class);
		if (verify == true) {
			CartModel cart = new CartModel(cartDto);
			BookModel book = restTemplate.getForObject(
					"http://BOOK-CONTROLLER/bookstore/getbyid/" + cartDto.getBook_Id() + "?token=" + token,
					BookModel.class);
			System.out.println(book.toString());
			if (cart.getBook_Id() == book.getBook_Id()) {
				cart.setUser_Id(tokenUtil.decodeToken(token));
				return cartRepository.save(cart);
			} else {
				throw new CartException("ID do not match !!!");
			}
		} else {
			throw new CartException("Token verification error !!!");
		}
	}

	@Override
	public List<CartModel> getAllCartItem(String token) {
		boolean verify = restTemplate.getForObject("http://BOOKSTORE-USER/bookstoreuser/verify?token=" + token,
				Boolean.class);
		if (verify == true) {
			List<CartModel> cartList = new ArrayList<CartModel>();
			cartRepository.findAll().forEach(cartList::add);
			return cartList;
		} else {
			throw new CartException("Token verification error !!!");
		}
	}

	@Override
	public CartModel getCartItemsById(String token, Long id) {
		boolean verify = restTemplate.getForObject("http://BOOKSTORE-USER/bookstoreuser/verify?token=" + token,
				Boolean.class);
		if (verify == true) {
			return cartRepository.findById(id).get();
		} else {
			throw new CartException("Token verification error !!!");
		}
	}

	@Override
	public CartModel updateCartItem(String token, Long id, CartDto cartDto) {
		boolean verify = restTemplate.getForObject("http://BOOKSTORE-USER/bookstoreuser/verify?token=" + token,
				Boolean.class);
		if (verify == true) {
			Optional<CartModel> cartData = cartRepository.findById(id);
			if (cartData.isPresent()) {
				cartData.get().setBook_Id(cartDto.getBook_Id());
				cartData.get().setQuantity(cartDto.getQuantity());
				cartRepository.save(cartData.get());
				return cartData.get();
			} else {
				throw new CartException("Id not available !!!");
			}
		} else {
			throw new CartException("Token verification error !!!");
		}
	}

	@Override
	public void removeCartItem(String token, Long id) {
		boolean verify = restTemplate.getForObject("http://BOOKSTORE-USER/bookstoreuser/verify?token=" + token,
				Boolean.class);
		if (verify == true) {
			Optional<CartModel> cartData = cartRepository.findById(id);
			if (cartData.isPresent()) {
				cartRepository.deleteById(id);
			} else {
				throw new CartException("Id not available !!!");
			}
		} else {
			throw new CartException("Token verification error !!!");
		}
	}

}
