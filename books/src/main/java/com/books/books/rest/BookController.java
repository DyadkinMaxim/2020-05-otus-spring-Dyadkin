package com.books.books.rest;

import com.books.books.models.Author;
import com.books.books.models.Book;
import com.books.books.models.Comment;
import com.books.books.models.Style;
import com.books.books.repositoriesSpringDataJPA.BookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Controller
public class BookController {

    private final BookRepository bookRepository;
    private final CommentController commentController;

    public BookController(BookRepository repository, CommentController commentController) {
        this.bookRepository = repository;
        this.commentController = commentController;
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
        model.addAttribute("book", book);
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
            Book book,
            Model model
    ) {
        Book savedBook = bookRepository.findById(book.getId()).orElseThrow(NotFoundException::new);
        savedBook.setBookName(book.getBookName());
        Book saved = bookRepository.save(savedBook);
        model.addAttribute(saved);
        return "redirect:/";
    }

    @GetMapping("/save")
    public String toSave() {
        return "save";
    }

    @PostMapping("/save")
    public String saveBook(
            String authorName,
            String styleName,
            String bookName,
            String commentText,
            Model model
    ) {
        Book book = new Book();
        Author author = new Author();
        Style style = new Style();
        Comment comment = new Comment();
        author.setAuthorName(authorName);
        style.setStyleName(styleName);
        comment.setCommentText(commentText);
        book.setAuthor(author);
        book.setStyle(style);
        book.setBookName(bookName);
        book.setComment(Arrays.asList(comment));
        Book saved = bookRepository.save(book);
        commentController.saveCommentInBook(commentText, saved, model);
        model.addAttribute(saved);
        return "redirect:/";
    }
}
