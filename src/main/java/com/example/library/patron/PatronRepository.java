package com.example.library.patron;

import com.example.library.book.Book;
import com.example.library.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatronRepository extends JpaRepository<Patron, String>, JpaSpecificationExecutor<Patron> {
    Page<Patron> findByNameContainingIgnoreCaseOrIdContainingIgnoreCase(String name, String id, Pageable pageable);
    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    Boolean existsByPersonalId(String personalId);

    boolean existsByUser(User user);

    Optional<Patron> findByUser(User user);
}
