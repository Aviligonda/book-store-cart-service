package com.bridgelabz.bookstorecartservice.service;

import com.bridgelabz.bookstorecartservice.dto.CartServiceDTO;
import com.bridgelabz.bookstorecartservice.model.CartServiceModel;
import com.bridgelabz.bookstorecartservice.util.Response;

import java.util.List;
/**
 * Purpose : ICartService to Show The all APIs
 * Version : 1.0
 *
 * @author : Aviligonda Sreenivasulu
 */
public interface ICartService {
    Response addCart(String token, CartServiceDTO cartServiceDTO, Long bookId);

    Response removingCart(String token, Long cartId);

    Response updateQuantity(String token, Long cartId, Long quantity);

    List<CartServiceModel> getAllCartItemsForUser(String token);

    List<CartServiceModel> getAllCartItems();

    Response verifyCartItem(Long cartId);
}
