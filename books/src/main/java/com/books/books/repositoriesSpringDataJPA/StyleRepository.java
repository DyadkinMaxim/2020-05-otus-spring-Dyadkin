package com.books.books.repositoriesSpringDataJPA;

import com.books.books.models.Style;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StyleRepository extends CrudRepository<Style, Long> {

    Style findByStyleNameContains(String name);
}
