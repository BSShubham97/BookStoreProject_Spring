package com.bridgelabz.bookstore.order_service.repository;

 import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.bookstore.order_service.model.OrderModel;

@Repository
public interface OrderRepository extends JpaRepository<OrderModel, Long> {

}
