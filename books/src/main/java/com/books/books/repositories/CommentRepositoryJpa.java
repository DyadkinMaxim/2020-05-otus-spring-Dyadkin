package com.books.books.repositories;

import com.books.books.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepositoryJpa {
    Optional<Long> save(Comment comment);

    List<Comment> findAll();

    Optional<Comment> findById(long id);

    int deleteById(long id);
}
