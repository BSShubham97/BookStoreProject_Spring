package com.bridgelabz.bookstore.order_service.services;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bridgelabz.bookstore.order_service.dto.OrderDto;
import com.bridgelabz.bookstore.order_service.model.BookModel;
import com.bridgelabz.bookstore.order_service.model.OrderModel;
import com.bridgelabz.bookstore.order_service.repository.OrderRepository;
import com.bridgelabz.bookstore.order_service.util.TokenUtil;

@Service
public class OrderService implements IOrderService {
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	TokenUtil tokenUtil;
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	ModelMapper modelMapper;

	@Override
	public OrderModel placeOrder(String token, OrderDto orderDto) {
		boolean verify = restTemplate.getForObject("http://BOOKSTORE-USER/bookstoreuser/verify?token="+token, Boolean.class);
		if(verify == true) {
		Long bookId = orderDto.getBookId();
//		OrderModel orderModel = new OrderModel(orderDto);
		OrderModel orderModel = modelMapper.map(orderDto, OrderModel.class);
		
		System.out.println("BookID entered by the user: "+bookId);
     	BookModel book = restTemplate.getForObject("http://BOOK-CONTROLLER/bookstore/getbyid/"+bookId+"?token="+token, BookModel.class);
		System.out.println(book.toString());
		Integer bookPrice = book.getBookPrice();
		System.out.println("Book Price: "+bookPrice);
//		if(book.getBookQuantity()> orderDto.getQuantity()) {
//			int remQuantity = book.getBookQuantity() - orderDto.getQuantity();
//			restTemplate.getForObject("http://BOOK-CONTROLLER/bookstore/changequantity?token="+token+"&id="+orderDto.getBookId()+"&quantity="+remQuantity,Book.class);
//			orderModel.setPrice(orderDto.getQuantity()*book.getBookPrice());
			orderModel.setOrderDate(LocalDate.now());
			orderModel.setUserId(tokenUtil.decodeToken(token));
			return orderRepository.save(orderModel);
		}
		return null;
		}


	@Override
	public List<OrderModel> getAllOrders(String token) {
		boolean verify = restTemplate.getForObject("http://BOOKSTORE-USER/bookstoreuser/verify?token=" + token,
				Boolean.class);
		if (verify == true) {
			List<OrderModel> orderDataList = new ArrayList<OrderModel>();
			orderRepository.findAll().forEach(orderDataList::add);
			return orderDataList;
		}
		return null;
	}

	@Override
	public void cancelOrder(String token, Long id) {
		boolean verify = restTemplate.getForObject("http://BOOKSTORE-USER/bookstoreuser/verify?token=" + token,
				Boolean.class);
		if (verify == true) {
			Optional<OrderModel> isOrderPresent = orderRepository.findById(id);
			if (isOrderPresent.isPresent()) {
				isOrderPresent.get().setCancel(true);
				orderRepository.save(isOrderPresent.get());

			}
		}
	}

	@Override
	public OrderModel getOrderById(String token, Long id) {
		boolean verify = restTemplate.getForObject("http://BOOKSTORE-USER/bookstoreuser/verify?token=" + token,
				Boolean.class);
		if (verify == true) {
			Optional<OrderModel> isOrderPresent = orderRepository.findById(id);
			if (isOrderPresent.isPresent()) {
				return orderRepository.findById(id).get();
			}
		}
		return null;
	}

}
