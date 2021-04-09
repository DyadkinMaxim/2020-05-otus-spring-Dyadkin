package com.books.books.MVCservice;

import com.books.books.models.Book;
import com.books.books.models.Comment;

import java.util.List;

public interface BookService {

    void save(Book book, List<Comment> comments);

    void update(Book book);
}
