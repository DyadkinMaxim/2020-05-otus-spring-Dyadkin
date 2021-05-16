package com.books.books.rest;

import com.books.books.MVCservice.BookServiceImpl;
import com.books.books.MVCservice.PermissionServiceImpl;
import com.books.books.converters.BookConverterImpl;
import com.books.books.dto.BookDTO;
import com.books.books.models.Book;
import com.books.books.repositoriesSpringDataJPA.AuthorRepository;
import com.books.books.repositoriesSpringDataJPA.BookRepository;
import com.books.books.repositoriesSpringDataJPA.StyleRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final BookConverterImpl bookConverterImpl;
    private final PermissionServiceImpl permissionServiceImpl;

    public BookController(BookRepository repository,
                          StyleRepository styleRepository,
                          AuthorRepository authorRepository,
                          CommentController commentController,
                          BookServiceImpl bookServiceImpl,
                          BookConverterImpl bookConverter,
                          PermissionServiceImpl permissionServiceImpl) {
        this.bookRepository = repository;
        this.commentController = commentController;
        this.styleRepository = styleRepository;
        this.authorRepository = authorRepository;
        this.bookServiceImpl = bookServiceImpl;
        this.bookConverterImpl = bookConverter;
        this.permissionServiceImpl = permissionServiceImpl;
    }

    @GetMapping("/api/books")
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream().map(bookConverterImpl::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/api/books/{id}")
    public BookDTO updateBook(@PathVariable(value = "id") long id) {
        Book book = bookRepository.findById(id).orElseThrow(NotFoundException::new);
        BookDTO bookDTO = bookConverterImpl.toDTO(book);
        return bookDTO;
    }

    @DeleteMapping("/api/books/{id}")
    @PreAuthorize("hasRole('admin')")
    public void deleteBook(@PathVariable(value = "id") long id) {
        bookRepository.deleteById(id);
    }

    @PutMapping("/api/books/{id}")
    @ExceptionHandler(NotFoundException.class)
    public void updateBook(
            @RequestBody BookDTO bookDTO
    ) {
        Book book = bookConverterImpl.toBook(bookDTO);
        bookServiceImpl.update(book);
    }


    @PostMapping("/api/books/newBook")
    @PreAuthorize("hasRole('admin')")
    public void saveBook(
            @RequestBody BookDTO bookDTO
    ) {
        Book newBook = bookConverterImpl.toBook(bookDTO);
        Book savedBook = bookServiceImpl.save(newBook, newBook.getComment());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        permissionServiceImpl.addPermissionForUser(savedBook, BasePermission.READ, authentication.getName());
    }
}
