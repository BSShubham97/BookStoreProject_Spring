package com.bridgelabz.bookstore.order_service.dto;

import java.util.Date;

import lombok.Data;

@Data
public class OrderDto {
	private Long orderId;
	private Date orderDate;
	private String price;
	private String quantity;
	private String address;
	private Long userId;
	private Long bookId;
	private boolean cancel;
}
