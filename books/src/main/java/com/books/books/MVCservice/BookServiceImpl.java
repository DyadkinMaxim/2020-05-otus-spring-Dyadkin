package com.books.books.MVCservice;

import com.books.books.models.Author;
import com.books.books.models.Book;
import com.books.books.models.Comment;
import com.books.books.models.Style;
import com.books.books.repositoriesSpringDataJPA.AuthorRepository;
import com.books.books.repositoriesSpringDataJPA.BookRepository;
import com.books.books.repositoriesSpringDataJPA.CommentRepository;
import com.books.books.repositoriesSpringDataJPA.StyleRepository;
import com.books.books.rest.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookServiceImpl implements  BookService{

    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;
    private final AuthorRepository authorRepository;
    private final StyleRepository styleRepository;
    private Author existingAuthor;
    private Style existingStyle;

    public BookServiceImpl(BookRepository bookRepository,
                           CommentRepository commentRepository,
                           AuthorRepository authorRepository,
                           StyleRepository styeRepository){
        this.bookRepository = bookRepository;
        this.commentRepository = commentRepository;
        this.authorRepository = authorRepository;
        this.styleRepository = styeRepository;
    }


    @Transactional
    public void save(Book book, List<Comment> comments){
        for(Comment comment : comments) {
            comment.setBook(book);
        }
        existingAuthor = authorRepository.findByAuthorNameContains(book.getAuthor().getAuthorName());
        existingStyle = styleRepository.findByStyleNameContains(book.getStyle().getStyleName());
        book.setAuthor(existingAuthor);
        book.setStyle(existingStyle);
        book.setComment(comments);
        bookRepository.save(book);
    }

    @Transactional
    public void update(Book book) {
        Book savedBook = bookRepository.findById(book.getId()).orElseThrow(NotFoundException::new);
        savedBook.setBookName(book.getBookName());
        existingAuthor = authorRepository.findByAuthorNameContains(book.getAuthor().getAuthorName());
        existingStyle = styleRepository.findByStyleNameContains(book.getStyle().getStyleName());
        savedBook.setAuthor(existingAuthor);
        savedBook.setStyle(existingStyle);
    }
}
