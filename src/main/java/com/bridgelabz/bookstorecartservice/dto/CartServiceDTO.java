package com.bridgelabz.bookstorecartservice.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
/**
 * Purpose : CartServiceDTO fields are Used to Create and Update Cart Details
 * Version : 1.0
 *
 * @author : Aviligonda Sreenivasulu
 */
@Data
public class CartServiceDTO {
    @NotNull(message = "quantity can't be null")
    private long quantity;
}
