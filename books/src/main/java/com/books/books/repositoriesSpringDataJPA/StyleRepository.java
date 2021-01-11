package com.books.books.repositoriesSpringDataJPA;

import com.books.books.models.Style;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StyleRepository extends CrudRepository<Style, Long> {
    List<Style> findAll();


    @Query("select s from Style s where s.styleName = :name")
    Style findByName(@Param("name") String name);
}
