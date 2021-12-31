package com.bridgelabz.bookstore.cart_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.bookstore.cart_service.dto.CartDto;
import com.bridgelabz.bookstore.cart_service.dto.ResponseDto;
import com.bridgelabz.bookstore.cart_service.model.CartModel;
import com.bridgelabz.bookstore.cart_service.services.ICartService;

@RestController
@RequestMapping("/cart")
public class CartController {
	@Autowired
	ICartService cartService;

	@PostMapping("/addtocart")
	public ResponseEntity<ResponseDto> addBookToCart(@RequestHeader String token, @RequestBody CartDto cartDto) {
		CartModel cartDataInfo = cartService.addToCart(token, cartDto);
		ResponseDto respDto = new ResponseDto("Added to Cart Succcessfully !!!", cartDataInfo);
		return new ResponseEntity<ResponseDto>(respDto, HttpStatus.OK);
	}

	@GetMapping("/getallcartitems")
	public ResponseEntity<ResponseDto> getAllCartItem(@RequestHeader String token) {
		List<CartModel> cartList = cartService.getAllCartItem(token);
		ResponseDto respDto = new ResponseDto("GET CALL Successful !!!", cartList);
		return new ResponseEntity<ResponseDto>(respDto, HttpStatus.OK);
	}

	@GetMapping("/getcartitemsbyid")
	public ResponseEntity<ResponseDto> getcartitemsById(@RequestHeader String token) {
		CartModel cartList = cartService.getCartItemsById(token);
		ResponseDto respDto = new ResponseDto("GET CALL Successful !!!", cartList);
		return new ResponseEntity<ResponseDto>(respDto, HttpStatus.OK);
	}
	@PutMapping("/updatecartitem/{id}")
	public ResponseEntity<ResponseDto> updateCartItem(@RequestHeader String token ,@PathVariable Long id, @RequestBody CartDto cartDto) {
	
			CartModel cartData = cartService.updateCartItem(token, id, cartDto);
			ResponseDto respDto = new ResponseDto("UPDATED Successfully", cartData);
		    return new ResponseEntity<ResponseDto>(respDto, HttpStatus.OK);
	}

	@DeleteMapping("/deletecartitem/{id}")
	public ResponseEntity<ResponseDto> removeCartItem(@RequestHeader String token,@PathVariable Long id) {
		cartService.removeCartItem(token, id);
		ResponseDto respDto = new ResponseDto("Deleted Book Details with id: "+id,"DATABASE UPDATED !!!");
		return new ResponseEntity<ResponseDto>(respDto, HttpStatus.OK);
	}
}