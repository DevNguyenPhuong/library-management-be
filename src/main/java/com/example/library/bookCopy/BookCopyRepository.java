package com.example.library.bookCopy;

import com.example.library.book.Book;
import com.example.library.category.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookCopyRepository extends JpaRepository<BookCopy, String>, JpaSpecificationExecutor<Book> {
    Page<BookCopy> findByBookId(String bookId, Pageable pageable);

    Page<BookCopy> findByBookIdAndIdContainingIgnoreCase(
            String bookId, String id, Pageable pageable);


}