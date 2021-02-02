package com.books.books.repositoriesSpringDataJPA;

import com.books.books.models.Author;
import org.springframework.data.repository.CrudRepository;


public interface AuthorRepository extends CrudRepository<Author, Long> {

    Author findByAuthorNameContains(String name);
}
