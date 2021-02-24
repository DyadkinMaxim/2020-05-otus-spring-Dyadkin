package com.books.books.MVCservice;

import com.books.books.models.Book;
import com.books.books.models.Comment;

public interface CommentService {

    void saveComment(Comment comment, long bookId);
}
