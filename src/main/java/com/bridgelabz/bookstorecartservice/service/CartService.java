package com.bridgelabz.bookstorecartservice.service;

import com.bridgelabz.bookstorecartservice.dto.CartServiceDTO;
import com.bridgelabz.bookstorecartservice.exception.UserException;
import com.bridgelabz.bookstorecartservice.model.CartServiceModel;
import com.bridgelabz.bookstorecartservice.repository.CartServiceRepository;
import com.bridgelabz.bookstorecartservice.util.BookResponse;
import com.bridgelabz.bookstorecartservice.util.Response;
import com.bridgelabz.bookstorecartservice.util.TokenUtil;
import com.bridgelabz.bookstorecartservice.util.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class CartService implements ICartService {
    @Autowired
    CartServiceRepository cartServiceRepository;
    @Autowired
    TokenUtil tokenUtil;
    @Autowired
    MailService mailService;
    @Autowired
    RestTemplate restTemplate;

    @Override
    public Response addCart(String token, CartServiceDTO cartServiceDTO, Long bookId) {
        UserResponse isUserPresent = restTemplate.getForObject("http://BS-USER-SERVICE:8080/userService/userVerification/" + token, UserResponse.class);
        if (isUserPresent.getStatusCode() == 200) {
            BookResponse isBookPresent = restTemplate.getForObject("http://BS-BOOK-SERVICE:8081/bookService/verifyBook/" + bookId, BookResponse.class);
            if (isBookPresent.getStatusCode() == 200) {
                CartServiceModel cartServiceModel = new CartServiceModel(cartServiceDTO);
                cartServiceModel.setBookId(bookId);
                cartServiceModel.setUserId(isUserPresent.getObject().getId());
                if (isBookPresent.getObject().getBookQuantity() >= cartServiceDTO.getQuantity()) {
                    cartServiceModel.setQuantity(cartServiceDTO.getQuantity());
                } else {
                    throw new UserException(400, cartServiceDTO.getQuantity() + " Books are not Availible ,Now only "
                            + isBookPresent.getObject().getBookQuantity() + " Books are Availible");
                }
                cartServiceModel.setTotalPrice((cartServiceDTO.getQuantity()) * (isBookPresent.getObject().getBookPrice()));
                cartServiceRepository.save(cartServiceModel);
                return new Response(200, "Success", cartServiceModel);
            }
        }
        return null;
    }

    @Override
    public Response removingCart(String token, Long cartId) {
        UserResponse isUserPresent = restTemplate.getForObject("http://BS-USER-SERVICE:8080/userService/userVerification/" + token, UserResponse.class);
        if (isUserPresent.getStatusCode() == 200) {
            Optional<CartServiceModel> isCartPresent = cartServiceRepository.findById(cartId);
            if (isCartPresent.isPresent()) {
                if (isCartPresent.get().getUserId() == isUserPresent.getObject().getId()) {
                    cartServiceRepository.delete(isCartPresent.get());
                    return new Response(200, "Success", isCartPresent.get());
                }
                throw new UserException(400, "No Cart Books found with this UserId");
            }
            throw new UserException(400, "No Cart Book found with this id");
        }
        return null;
    }

    @Override
    public Response updateQuantity(String token, Long cartId, Long quantity) {
        UserResponse isUserPresent = restTemplate.getForObject("http://BS-USER-SERVICE:8080/userService/userVerification/" + token, UserResponse.class);
        if (isUserPresent.getStatusCode() == 200) {
            Optional<CartServiceModel> isCartPresent = cartServiceRepository.findById(cartId);
            if (isCartPresent.isPresent()) {
                BookResponse isBookPresent = restTemplate.getForObject("http://BS-BOOK-SERVICE:8081/bookService/verifyBook/"
                        + isCartPresent.get().getBookId(), BookResponse.class);
                if (isCartPresent.get().getUserId() == isUserPresent.getObject().getId()) {
                    isCartPresent.get().setQuantity(quantity);
                    isCartPresent.get().setTotalPrice((quantity) * (isBookPresent.getObject().getBookPrice()));
                    cartServiceRepository.save(isCartPresent.get());
                    return new Response(200, "Success", isCartPresent.get());
                }
                throw new UserException(400, "No Cart Books found with this UserId");
            }
            throw new UserException(400, "No Cart Book found with this id");
        }
        return null;
    }

    @Override
    public List<CartServiceModel> getAllCartItemsForUser(String token) {
        UserResponse isUserPresent = restTemplate.getForObject("http://BS-USER-SERVICE:8080/userService/userVerification/" + token, UserResponse.class);
        if (isUserPresent.getStatusCode() == 200) {
            Optional<CartServiceModel> isUserIdPresent = cartServiceRepository.findByUserId(isUserPresent.getObject().getId());
            if (isUserIdPresent.isPresent()) {
                List<CartServiceModel> isCartPresent = cartServiceRepository.findAll();
                if (isCartPresent.size() > 0) {
                    return isCartPresent;
                }
                throw new UserException(400, "Cart items Not found");
            }
            throw new UserException(400, "No Cart items Found with this UserId");
        }
        return null;
    }

    @Override
    public List<CartServiceModel> getAllCartItems() {
        List<CartServiceModel> isCartPresent = cartServiceRepository.findAll();
        if (isCartPresent.size() > 0) {
            return isCartPresent;
        }
        throw new UserException(400, "Cart items Not found");
    }
}
