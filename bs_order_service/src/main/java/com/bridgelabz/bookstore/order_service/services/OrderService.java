package com.bridgelabz.bookstore.order_service.services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstore.order_service.dto.OrderDto;
import com.bridgelabz.bookstore.order_service.model.OrderModel;
import com.bridgelabz.bookstore.order_service.repository.OrderRepository;
import com.bridgelabz.bookstore.order_service.util.TokenUtil;

@Service
public class OrderService implements IOrderService {
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	TokenUtil tokenUtil;

	@Override
	public OrderModel placeOrder(String token, OrderDto orderDto) {
		Long userId = tokenUtil.decodeToken(token);
		OrderModel orderModel = new OrderModel(orderDto);
		orderModel.setOrderDate(new Date(System.currentTimeMillis()));
		orderModel.setUserId(userId);
		return orderRepository.save(orderModel);
	}

	@Override
	public List<OrderModel> getAllOrders() {
		List<OrderModel> orderDataList = new ArrayList<OrderModel>();
		orderRepository.findAll().forEach(orderDataList::add);
		return orderDataList;
	}

	@Override
	public void cancelOrder(String token, Long id) {
		Long tokenId = tokenUtil.decodeToken(token);
		Optional<OrderModel> isOrderPresent = orderRepository.findById(id);
		if (isOrderPresent.isPresent()) {
			isOrderPresent.get().setCancel(true);
			orderRepository.save(isOrderPresent.get());
		}

	}

	@Override
	public OrderModel getOrderById(String token, Long id) {
//		Long orderId = tokenUtil.decodeToken(token);
		return orderRepository.findById(id).get();
	}

}
