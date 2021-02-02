package com.books.books.repositories;


import com.books.books.models.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepositoryJpa {
    Optional<Long> save(Author author);

    List<Author> findAll();

    Optional<Author> findById(long id);

    Author findByName(String name);

    void deleteById(long id);
}
