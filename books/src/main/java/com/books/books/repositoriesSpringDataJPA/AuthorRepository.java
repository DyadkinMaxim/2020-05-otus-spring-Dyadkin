package com.books.books.repositoriesSpringDataJPA;

import com.books.books.models.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuthorRepository extends CrudRepository<Author, Long> {
    List<Author> findAll();

    @Query("select a from Author a where a.authorName = :name")
    Author findByName(@Param("name") String name);
}
