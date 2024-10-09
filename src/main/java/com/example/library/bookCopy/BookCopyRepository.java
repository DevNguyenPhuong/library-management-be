package com.example.library.bookCopy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookCopyRepository extends JpaRepository<BookCopy, String> {
    List<BookCopy> findByBookId(String bookId);
}
