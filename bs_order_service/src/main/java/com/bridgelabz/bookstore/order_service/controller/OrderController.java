package com.bridgelabz.bookstore.order_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.bookstore.order_service.dto.OrderDto;
import com.bridgelabz.bookstore.order_service.dto.ResponseDto;
import com.bridgelabz.bookstore.order_service.model.OrderModel;
import com.bridgelabz.bookstore.order_service.services.IOrderService;

@RestController
@RequestMapping("/orderservice")
public class OrderController {

	@Autowired
	IOrderService orderService;

	@GetMapping("/getorders")
	public ResponseEntity<ResponseDto> getAllOrders( @RequestParam String token) {
		List<OrderModel> orderList = orderService.getAllOrders(token);
		ResponseDto respDto = new ResponseDto("Get call for all orders successful", orderList);
		return new ResponseEntity<ResponseDto>(respDto, HttpStatus.OK);
	}

	@PostMapping("/placeorder")
	public ResponseEntity<ResponseDto> placeOrder(@RequestParam String token, @RequestBody OrderDto orderDto) {
		OrderModel orderDataInfo = orderService.placeOrder(token, orderDto);
		ResponseDto respDto = new ResponseDto("Added Order Succcessfully !!!", orderDataInfo);
		return new ResponseEntity<ResponseDto>(respDto, HttpStatus.OK);
	}

	@GetMapping("/getOrderById/{id}")
	public ResponseEntity<ResponseDto> getOrderById(@RequestParam String token, @PathVariable Long id) {
		OrderModel orderDataInfo = orderService.getOrderById(token, id);
		ResponseDto respDto = new ResponseDto("GET CALL FOR ORDER:" + id + " Succcessfully !!!", orderDataInfo);
		return new ResponseEntity<ResponseDto>(respDto, HttpStatus.OK);
	}

	@DeleteMapping("/cancelOrder/{id}")
	public ResponseEntity<ResponseDto> cancelOrder(@RequestParam String token, @PathVariable Long id) {
		orderService.cancelOrder(token, id);
		ResponseDto respDto = new ResponseDto("Deleted ORDER Details with id: " + id, "DATABASE UPDATED !!!");
		return new ResponseEntity<ResponseDto>(respDto, HttpStatus.OK);
	}
}