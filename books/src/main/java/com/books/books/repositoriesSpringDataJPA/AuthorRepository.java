package com.books.books.repositoriesSpringDataJPA;

import com.books.books.models.Author;
import com.books.books.models.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface AuthorRepository extends CrudRepository<Author, Long> {

    List<Author> findAll();

    Author findByAuthorNameContains(String name);
}
