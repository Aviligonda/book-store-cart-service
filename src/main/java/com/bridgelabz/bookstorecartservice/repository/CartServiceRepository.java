package com.bridgelabz.bookstorecartservice.repository;

import com.bridgelabz.bookstorecartservice.model.CartServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartServiceRepository extends JpaRepository<CartServiceModel,Long> {
    Optional<CartServiceModel> findByUserId(Long id);
}
