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

/**
 * Purpose : CartService to Implement the Business Logic
 * Version : 1.0
 *
 * @author : Aviligonda Sreenivasulu
 */
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

    /**
     * Purpose : Implement the Logic of Add Cart
     *
     * @author : Aviligonda Sreenivasulu
     * @Param :  cartServiceDTO,token,bookId
     */
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
                    long quantity = isBookPresent.getObject().getBookQuantity() - cartServiceModel.getQuantity();
                    isBookPresent.getObject().setBookQuantity(quantity);

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

    /**
     * Purpose : Implement the Logic of Removing Cart item
     *
     * @author : Aviligonda Sreenivasulu
     * @Param :  cartId,token
     */
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

    /**
     * Purpose : Implement the Logic of Update quantity
     *
     * @author : Aviligonda Sreenivasulu
     * @Param :  cartId,token,quantity
     */
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

    /**
     * Purpose : Implement the Logic of Get All Cart items for particular User
     *
     * @author : Aviligonda Sreenivasulu
     * @Param :  token
     */
    @Override
    public List<CartServiceModel> getAllCartItemsForUser(String token) {
        UserResponse isUserPresent = restTemplate.getForObject("http://BS-USER-SERVICE:8080/userService/userVerification/" + token, UserResponse.class);
        if (isUserPresent.getStatusCode() == 200) {
            List<CartServiceModel> isCartPresent = cartServiceRepository.findByUserId(isUserPresent.getObject().getId());
            if (isCartPresent.size() > 0) {
                return isCartPresent;
            }
            throw new UserException(400, "Cart items Not found");
        }
        throw new UserException(400, "No Cart items Found with this UserId");
    }

    /**
     * Purpose : Implement the Logic of Get All Cart items
     *
     * @author : Aviligonda Sreenivasulu
     * @Param :
     */
    @Override
    public List<CartServiceModel> getAllCartItems() {
        List<CartServiceModel> isCartPresent = cartServiceRepository.findAll();
        if (isCartPresent.size() > 0) {
            return isCartPresent;
        }
        throw new UserException(400, "Cart items Not found");
    }

    /**
     * Purpose : Implement the Logic of Verify the Cart item
     *
     * @author : Aviligonda Sreenivasulu
     * @Param : cartId
     */
    @Override
    public Response verifyCartItem(Long cartId) {
        Optional<CartServiceModel> isCartPresent = cartServiceRepository.findById(cartId);
        if (isCartPresent.isPresent()) {
            return new Response(200, "Cart item Found", isCartPresent.get());
        }
        throw new UserException(400, "No Cart item found with this id");
    }

}
