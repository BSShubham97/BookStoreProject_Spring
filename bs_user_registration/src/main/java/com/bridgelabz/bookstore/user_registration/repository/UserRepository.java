package com.bridgelabz.bookstore.user_registration.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.bookstore.user_registration.model.UserModel;


@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
	@Query(value = "select * from book_store_user_registration where email= :email", nativeQuery = true)
	Optional<UserModel> findByEmail(String email);
}
