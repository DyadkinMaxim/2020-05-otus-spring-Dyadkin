package com.books.books.repositoriesSpringDataJPA;

import com.books.books.models.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {
}
