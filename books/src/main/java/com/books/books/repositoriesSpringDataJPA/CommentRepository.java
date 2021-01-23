package com.books.books.repositoriesSpringDataJPA;

import com.books.books.models.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long> {
}
