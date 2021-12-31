package com.bridgelabz.bookstore.cart_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.bookstore.cart_service.model.CartModel;

@Repository
public interface CartRepository extends JpaRepository<CartModel, Long> {

}
