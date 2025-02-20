package com.example.library.cart_item;

import com.example.library.Shopping_session.ShoppingSession;
import com.example.library.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, String> {
    List<CartItem> findAllByShoppingSession(ShoppingSession shoppingSession);
    boolean existsByBookAndShoppingSession(Book book, ShoppingSession shoppingSession);
    Optional<CartItem> findByBookAndShoppingSession(Book book, ShoppingSession shoppingSession);
}
