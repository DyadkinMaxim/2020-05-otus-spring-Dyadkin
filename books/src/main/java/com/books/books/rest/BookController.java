package com.books.books.rest;

import com.books.books.MVCservice.BookServiceImpl;
import com.books.books.dto.BookDTO;
import com.books.books.models.Book;
import com.books.books.repositoriesSpringDataJPA.AuthorRepository;
import com.books.books.repositoriesSpringDataJPA.BookRepository;
import com.books.books.repositoriesSpringDataJPA.StyleRepository;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class BookController {

    private final BookRepository bookRepository;
    private final StyleRepository styleRepository;
    private final AuthorRepository authorRepository;
    private final CommentController commentController;
    private final BookServiceImpl bookServiceImpl;

    public BookController(BookRepository repository,
                          StyleRepository styleRepository,
                          AuthorRepository authorRepository,
                          CommentController commentController,
                          BookServiceImpl bookServiceImpl) {
        this.bookRepository = repository;
        this.commentController = commentController;
        this.styleRepository = styleRepository;
        this.authorRepository = authorRepository;
        this.bookServiceImpl = bookServiceImpl;
    }

    @GetMapping("/api/books")
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream().map(BookDTO::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/api/books/{id}")
    public BookDTO updateBook(@PathVariable(value = "id") long id) {
        Book book = bookRepository.findById(id).orElseThrow(NotFoundException::new);
        BookDTO bookDTO = BookDTO.toDTO(book);
        return bookDTO;
    }

    @DeleteMapping("/api/books/{id}")
    public void deleteBook(@PathVariable(value = "id") long id) {
        bookRepository.deleteById(id);
    }

    @PutMapping("/api/books/{id}")
    @ExceptionHandler(NotFoundException.class)
    public void updateBook(
            @RequestBody BookDTO bookDTO
    ) {
        Book book = BookDTO.toBook(bookDTO);
        bookServiceImpl.update(book);
    }


    @PostMapping("/api/books/newBook")
    public void saveBook(
            @RequestBody BookDTO bookDTO
    ) {
        Book newBook = BookDTO.toBook(bookDTO);
        bookServiceImpl.save(newBook, newBook.getComment());
    }
}
