package com.books.books.repositoriesSpringDataJPA;

import com.books.books.models.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long> {
    List<Book> findAll();

}
