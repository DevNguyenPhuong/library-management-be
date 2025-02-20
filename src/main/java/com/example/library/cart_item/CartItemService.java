package com.example.library.cart_item;


import com.example.library.Shopping_session.ShoppingSession;
import com.example.library.Shopping_session.ShoppingSessionService;
import com.example.library.book.Book;
import com.example.library.book.BookService;
import com.example.library.dto.CartItem.CartItemCreationRequest;
import com.example.library.dto.CartItem.CartItemResponse;
import com.example.library.dto.CartItem.CartItemUpdateRequest;
import com.example.library.exception.AppException;
import com.example.library.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final ShoppingSessionService shoppingSessionService;
    private final CartItemMapper cartItemMapper;
    private final BookService bookService;

    public CartItemResponse createCartItem(CartItemCreationRequest request) {
        Book book = bookService.findBookById(request.getBookId());
        ShoppingSession shoppingSession = shoppingSessionService.findShoppingSession(request.getSessionId());

        // Check if the cart item already exists
        CartItem cartItem;
        if (cartItemRepository.existsByBookAndShoppingSession(book, shoppingSession)) {
            CartItem oldCartItem = cartItemRepository.findByBookAndShoppingSession(book, shoppingSession)
                    .orElseThrow(() -> new AppException(ErrorCode.ITEMS_NOT_FOUND));

            oldCartItem.setQuantity(oldCartItem.getQuantity() + request.getQuantity());
            cartItem = oldCartItem;
        } else {
            cartItem = new CartItem();
            cartItem.setBook(book);
            cartItem.setShoppingSession(shoppingSession);
            cartItem.setQuantity(request.getQuantity());
        }

        try {
            cartItem = cartItemRepository.save(cartItem);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorCode.ITEM_ALREADY_EXISTS);
        }

        return cartItemMapper.toCartItemResponse(cartItem);
    }


    public List<CartItemResponse> getAllBySession(String sessionId) {
        ShoppingSession shoppingSession = shoppingSessionService.findShoppingSession(sessionId);
        var cartItems = cartItemRepository.findAllByShoppingSession(shoppingSession);
            return cartItems.stream().map(cartItemMapper::toCartItemResponse).toList();
    }

    public CartItemResponse updateCartItem(String cartItemId, CartItemUpdateRequest request) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new AppException(ErrorCode.ITEMS_NOT_FOUND));
        cartItem.setQuantity(request.getQuantity());

        try {
            cartItem = cartItemRepository.save(cartItem);
        }catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorCode.ITEM_ALREADY_EXISTS);
        }

        return cartItemMapper.toCartItemResponse(cartItem);
    }

    public void deleteCartItem(String cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new AppException(ErrorCode.ITEMS_NOT_FOUND));
        cartItemRepository.delete(cartItem);
    }
}
