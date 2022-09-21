package com.bridgelabz.bookstorecartservice.repository;

import com.bridgelabz.bookstorecartservice.model.CartServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
/**
 * Purpose : CartServiceRepository Are Used to Store the Data into Database
 * Version : 1.0
 * @author : Aviligonda Sreenivasulu
 * */
public interface CartServiceRepository extends JpaRepository<CartServiceModel,Long> {
    @Query(value = "select * from cart where user_id =:userId", nativeQuery = true)
    List<CartServiceModel> findByUserId(Long userId);
}
