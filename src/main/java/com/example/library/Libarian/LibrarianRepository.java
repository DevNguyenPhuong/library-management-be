package com.example.library.Libarian;

import com.example.library.patron.Patron;
import com.example.library.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LibrarianRepository extends JpaRepository<Librarian, String>, JpaSpecificationExecutor<Librarian> {
    Page<Librarian> findByNameContainingIgnoreCaseOrIdContainingIgnoreCase(String name, String id, Pageable pageable);
    boolean existsByEmail(String email);
    boolean existsByPhone(String username);
    boolean existsByUser(User user);

    Optional<Librarian> findByUser(User user);
}
