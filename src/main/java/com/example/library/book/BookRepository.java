package com.example.library.book;

import com.example.library.category.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, String>, JpaSpecificationExecutor<Book> {
    Page<Book> findByTitleContainingIgnoreCaseOrAuthors_NameContainingIgnoreCase(String title, String author, Pageable pageable);

    @Query("SELECT COUNT(b) FROM Book b JOIN b.categories c WHERE c = :category")
    long countByCategories(@Param("category") Category category);
}