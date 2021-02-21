package com.books.books.MVCservice;

import com.books.books.models.Book;
import com.books.books.models.Comment;
import com.books.books.repositoriesSpringDataJPA.BookRepository;
import com.books.books.repositoriesSpringDataJPA.CommentRepository;
import com.books.books.rest.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    public CommentServiceImpl(CommentRepository commentRepository, BookRepository bookRepository){
        this.commentRepository = commentRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional
    public void saveComment(Comment comment, long bookId){
        Book book = bookRepository.findById(bookId).orElseThrow(NotFoundException::new);
        comment.setBook(book);
        commentRepository.save(comment);
    }
}
