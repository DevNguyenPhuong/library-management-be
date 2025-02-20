package com.example.library.Shopping_session;

import com.example.library.constant.ShoppingSessionStatus;
import com.example.library.patron.Patron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface  ShoppingSessionRepository extends JpaRepository<ShoppingSession, String> {
    Optional<ShoppingSession> findByPatronAndStatus(Patron patron, ShoppingSessionStatus status);
    boolean existsByPatronAndStatus(Patron patron, ShoppingSessionStatus status);
}
