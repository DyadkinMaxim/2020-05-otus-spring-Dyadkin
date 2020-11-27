package com.books.books.repositories;

import com.books.books.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepositoryJpa {
    Optional<Long> save(Book book);

    List<Book> findAll();

    Optional<Book> findById(long id);

    Book findByName(String name);

    int updateBookName(Book book);

    int deleteById(long id);

}
