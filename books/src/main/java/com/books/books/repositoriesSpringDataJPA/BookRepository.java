package com.books.books.repositoriesSpringDataJPA;

import com.books.books.models.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends CrudRepository<Book, Long> {

    @PostFilter("hasPermission(filterObject, 'READ')")
    List<Book> findAll();

    @PostAuthorize("hasPermission(returnObject.orElse(null), 'READ')")
    Optional<Book> findById(long id);
}
