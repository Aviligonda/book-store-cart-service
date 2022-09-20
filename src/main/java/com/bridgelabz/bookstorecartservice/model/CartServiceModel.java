package com.bridgelabz.bookstorecartservice.model;

import com.bridgelabz.bookstorecartservice.dto.CartServiceDTO;
import lombok.Data;

import javax.persistence.*;
/**
 * Purpose : CartServiceModel Are Used Create A table and connection to Database
 * Version : 1.0
 *
 * @author : Aviligonda Sreenivasulu
 */
@Data
@Entity
@Table(name = "cart")
public class CartServiceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long userId;
    private long bookId;
    private long quantity;
    private long totalPrice;

    public CartServiceModel(CartServiceDTO cartServiceDTO) {
        this.quantity = cartServiceDTO.getQuantity();
    }

    public CartServiceModel() {
    }
}
