package com.bridgelabz.bookstore.book_controller.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.bookstore.book_controller.model.BookModel;

@Repository
public interface BookRepository extends JpaRepository<BookModel, Long> {

}
