package com.bridgelabz.bookstore.order_service.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bridgelabz.bookstore.order_service.dto.OrderDto;

import lombok.Data;
@Data
@Entity
@Table(name="Orders")
public class OrderModel {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="Order_ID")
   	private Long orderId;
	private Date orderDate;
	private String price;
	private String quantity;
	private String address;
	private Long userId;
	private Long bookId;
	private boolean cancel;
	
	public OrderModel(OrderDto orderDto) {
		super();
		this.orderId = orderDto.getOrderId();
		this.orderDate = orderDto.getOrderDate();
		this.price = orderDto.getPrice();
		this.quantity = orderDto.getQuantity();
		this.address = orderDto.getAddress();
		this.userId = orderDto.getUserId();
		this.bookId = orderDto.getBookId();
		this.cancel = orderDto.isCancel();
	}

	public OrderModel() {
		super();
	}
	
		
}
