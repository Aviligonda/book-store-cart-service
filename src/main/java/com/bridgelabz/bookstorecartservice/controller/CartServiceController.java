package com.bridgelabz.bookstorecartservice.controller;

import com.bridgelabz.bookstorecartservice.dto.CartServiceDTO;
import com.bridgelabz.bookstorecartservice.model.CartServiceModel;
import com.bridgelabz.bookstorecartservice.service.ICartService;
import com.bridgelabz.bookstorecartservice.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Purpose :REST ApIs Controller
 * Version : 1.0
 *
 * @author : Aviligonda Sreenivasulu
 */

@RestController
@RequestMapping("/cartService")
public class CartServiceController {
    @Autowired
    ICartService cartService;

    /**
     * Purpose :  Added To Cart
     *
     * @author : Aviligonda Sreenivasulu
     * @Param : token,cartServiceDTO
     */
    @PostMapping("/addCart")
    public ResponseEntity<Response> addCart(@RequestHeader String token,
                                            @RequestParam Long bookId,
                                            @Valid @RequestBody CartServiceDTO cartServiceDTO) {
        Response response = cartService.addCart(token, cartServiceDTO, bookId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("removingCart/{cartId}")
    public ResponseEntity<Response> removingCart(@RequestHeader String token,
                                                 @PathVariable Long cartId) {
        Response response = cartService.removingCart(token, cartId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("updateQuantity/{cartId}")
    public ResponseEntity<Response> updateQuantity(@RequestHeader String token,
                                                   @PathVariable Long cartId,
                                                   @RequestParam Long quantity) {
        Response response = cartService.updateQuantity(token, cartId, quantity);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getAllCartItemsForUser")
    public ResponseEntity<List<?>> getAllCartItemsForUser(@RequestHeader String token
    ) {
        List<CartServiceModel> response = cartService.getAllCartItemsForUser(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getAllCartItems")
    public ResponseEntity<List<?>> getAllCartItems() {
        List<CartServiceModel> response = cartService.getAllCartItems();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
