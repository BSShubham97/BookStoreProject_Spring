package com.bridgelabz.bookstore.order_service.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class OrderDto {
	private Long orderId;
	private LocalDate orderDate;
	private Integer price;
	private Integer quantity;
	private String address;
	private Long userId;
	private Long bookId;
	private boolean cancel;
}
