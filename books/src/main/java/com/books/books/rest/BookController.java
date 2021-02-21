package com.books.books.rest;

import com.books.books.MVCservice.BookService;
import com.books.books.MVCservice.BookServiceImpl;
import com.books.books.models.Author;
import com.books.books.models.Book;
import com.books.books.models.Comment;
import com.books.books.models.Style;
import com.books.books.repositoriesSpringDataJPA.AuthorRepository;
import com.books.books.repositoriesSpringDataJPA.BookRepository;
import com.books.books.repositoriesSpringDataJPA.StyleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Controller
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

    @GetMapping("/")
    public String listBooks(Model model) {
        List<Book> books = (List<Book>) bookRepository.findAll();
        model.addAttribute("books", books);
        return "list";
    }

    @GetMapping("/edit")
    @ExceptionHandler(NotFoundException.class)
    public String editBook(@RequestParam("id") long id, Model model) {
        Book book = bookRepository.findById(id).orElseThrow(NotFoundException::new);
        List<Style> styles = (List<Style>) styleRepository.findAll();
        List<Author> authors = (List<Author>) authorRepository.findAll();
        Author author = new Author();
        model.addAttribute("book", book);
        model.addAttribute("styles", styles);
        model.addAttribute("authors", authors);
        return "edit";
    }

    @GetMapping("/delete")
    public String deleteBook(@RequestParam("id") long id, Model model) {
        bookRepository.deleteById(id);
        return "redirect:/";
    }

    @PostMapping("/edit")
    @ExceptionHandler(NotFoundException.class)
    public String updateBook(
            Book book
    ) {
      bookServiceImpl.update(book);
        return "redirect:/";
    }

    @GetMapping("/save")
    public String toSave(Model model) {
        List<Style> styles = (List<Style>) styleRepository.findAll();
        List<Author> authors = (List<Author>) authorRepository.findAll();
        Book newBook = new Book();
        Comment newComment = new Comment();
        model.addAttribute("book", newBook);
        model.addAttribute("comment", newComment);
        model.addAttribute("styles", styles);
        model.addAttribute("authors", authors);
        return "save";
    }

    @PostMapping("/save")
    public String saveBook(
            Book book,
            Comment comment
    ) {
        bookServiceImpl.save(book, comment);
        return "redirect:/";
    }
}
