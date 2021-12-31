package com.bridgelabz.bookstore.order_service.services;

import java.util.List;

import com.bridgelabz.bookstore.order_service.dto.OrderDto;
import com.bridgelabz.bookstore.order_service.model.OrderModel;

public interface IOrderService {
    
	
	OrderModel placeOrder(String token, OrderDto orderDto);
	List<OrderModel>getAllOrders();
	OrderModel getOrderById(String token , Long id);
	void cancelOrder(String token , Long id); 
}
