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
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BookMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publisher", ignore = true)
    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "imageUrl", ignore = true)
    Book toBook(BookRequest request);

    @Mapping(target = "publisher", qualifiedByName = "toPublisherSimpleResponse")
    @Mapping(target = "authors", qualifiedByName = "toAuthorSimpleResponseSet")
    @Mapping(target = "categories", qualifiedByName = "toCategorySimpleResponseSet")
    @Mapping(target = "imageUrl", qualifiedByName = "toFullImgURL")
    BookResponse toBookResponse(Book book);

    @Named("toFullImgURL")
    default String toFullImgURL(String  imageUrl) {
        return "http://localhost:8080/library/images/books/" + imageUrl;
    }


    @Named("toPublisherSimpleResponse")
    default PublisherSimpleResponse toPublisherSimpleResponse(Publisher publisher) {
        return publisher != null ? new PublisherSimpleResponse(publisher.getId(), publisher.getName()) : null;
    }

    @Named("toAuthorSimpleResponse")
    default AuthorSimpleResponse toAuthorSimpleResponse(Author author) {
        return author != null ? new AuthorSimpleResponse(author.getId(), author.getName()) : null;
    }

    @Named("toAuthorSimpleResponseSet")
    default Set<AuthorSimpleResponse> toAuthorSimpleResponseSet(Set<Author> authors) {
        if (authors == null) {
            return Collections.emptySet();
        }
        return authors.stream()
                .map(this::toAuthorSimpleResponse)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    @Named("toCategorySimpleResponse")
    default CategorySimpleResponse toCategorySimpleResponse(Category category) {
        return category != null ? new CategorySimpleResponse(category.getId(), category.getName()) : null;
    }

    @Named("toCategorySimpleResponseSet")
    default Set<CategorySimpleResponse> toCategorySimpleResponseSet(Set<Category> categories) {
        if (categories == null) {
            return Collections.emptySet();
        }
        return categories.stream()
                .map(this::toCategorySimpleResponse)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    @Mapping(target = "publisher", ignore = true)
    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "imageUrl", ignore = true)
    void updateBook(@MappingTarget Book book, BookRequest bookRequest);
}