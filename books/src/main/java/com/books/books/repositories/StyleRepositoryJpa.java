package com.books.books.repositories;


import com.books.books.models.Style;

import java.util.List;
import java.util.Optional;

public interface StyleRepositoryJpa {
    Optional<Long> save(Style style);

    List<Style> findAll();

    Optional<Style> findById(long id);

    Style findByName(String name);

    void deleteById(long id);
}
