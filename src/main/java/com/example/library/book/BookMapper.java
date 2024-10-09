package com.example.library.book;

import com.example.library.dto.book.BookRequest;
import com.example.library.dto.author.AuthorSimpleResponse;
import com.example.library.dto.book.BookResponse;
import com.example.library.dto.Category.CategorySimpleResponse;
import com.example.library.dto.publisher.PublisherSimpleResponse;
import com.example.library.author.Author;
import com.example.library.category.Category;
import com.example.library.publisher.Publisher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BookMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publisher", ignore = true)
    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "categories", ignore = true)
    Book toBook(BookRequest request);


    @Mapping(target = "publisher", qualifiedByName = "toPublisherSimpleResponse")
    @Mapping(target = "authors", qualifiedByName = "toAuthorSimpleResponseSet")
    @Mapping(target = "categories", qualifiedByName = "toCategorySimpleResponseSet")
    BookResponse toBookResponse(Book book);

    @Named("toPublisherSimpleResponse")
    default PublisherSimpleResponse toPublisherSimpleResponse(Publisher publisher) {
        return new PublisherSimpleResponse(publisher.getId(), publisher.getName());
    }

    @Named("toAuthorSimpleResponse")
    default AuthorSimpleResponse toAuthorSimpleResponse(Author author) {
        return new AuthorSimpleResponse(author.getId(), author.getName());
    }

    @Named("toAuthorSimpleResponseSet")
    default Set<AuthorSimpleResponse> toAuthorSimpleResponseSet(Set<Author> authors) {
        return authors.stream()
                .map(this::toAuthorSimpleResponse)
                .collect(Collectors.toSet());
    }

    @Named("toCategorySimpleResponse")
    default CategorySimpleResponse toCategorySimpleResponse(Category category) {
        return new CategorySimpleResponse(category.getId(), category.getName());
    }

    @Named("toCategorySimpleResponseSet")
    default Set<CategorySimpleResponse> toCategorySimpleResponseSet(Set<Category> categories) {
        return categories.stream()
                .map(this::toCategorySimpleResponse)
                .collect(Collectors.toSet());
    }
}