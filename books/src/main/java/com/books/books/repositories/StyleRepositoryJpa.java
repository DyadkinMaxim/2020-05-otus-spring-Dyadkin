package com.books.books.repositories;


import com.books.books.models.Author;
import com.books.books.models.Book;
import com.books.books.models.Style;

import java.util.List;
import java.util.Optional;

public interface StyleRepositoryJpa {
    Optional<Long> save(Style style);

    List<Style> findAll();

    Optional<Style> findById(long id);

    Style findByName(String name);

    List<Book> findBooksByStyle(String styleName);

    List<Author> findAuthorsByStyle(String styleName);

    int deleteById(long id);
}
