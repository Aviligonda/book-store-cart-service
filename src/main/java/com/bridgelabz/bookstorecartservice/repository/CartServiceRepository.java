package com.bridgelabz.bookstorecartservice.repository;

import com.bridgelabz.bookstorecartservice.model.CartServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
/**
 * Purpose : CartServiceRepository Are Used to Store the Data into Database
 * Version : 1.0
 * @author : Aviligonda Sreenivasulu
 * */
public interface CartServiceRepository extends JpaRepository<CartServiceModel,Long> {
    Optional<CartServiceModel> findByUserId(Long id);
}
