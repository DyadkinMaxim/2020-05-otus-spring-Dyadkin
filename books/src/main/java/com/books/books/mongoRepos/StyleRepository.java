package com.books.books.mongoRepos;

import com.books.books.models.Style;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StyleRepository extends MongoRepository<Style, Long> {
    Style findByStyleNameContains(String name);
}
