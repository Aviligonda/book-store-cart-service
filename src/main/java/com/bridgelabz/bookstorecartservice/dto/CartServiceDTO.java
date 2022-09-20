package com.bridgelabz.bookstorecartservice.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CartServiceDTO {
    @NotNull(message = "quantity can't be null")
    private long quantity;
}
