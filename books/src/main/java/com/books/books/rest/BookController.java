package com.books.books.rest;

import com.books.books.models.Author;
import com.books.books.models.Book;
import com.books.books.models.Style;
import com.books.books.repositoriesSpringDataJPA.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class BookController {

    private final BookRepository repository;

    @Autowired
    public BookController(BookRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String listBooks(Model model) {
        List<Book> books = (List<Book>) repository.findAll();
        model.addAttribute("books", books);
        return "list";
    }

    @GetMapping("/edit")
    public String editBook(@RequestParam("id") long id, Model model) {
        Book book = repository.findById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("book", book);
        return "edit";
    }

    @GetMapping("/delete")
    public String deleteBook(@RequestParam("id") long id, Model model) {
        repository.deleteById(id);
        return "redirect:/";
    }

    @PostMapping("/edit")
    public String updateBook(
            Book book,
            Model model
    ) {
        Book savedBook = repository.findById(book.getId()).orElseThrow(NotFoundException::new);
        savedBook.setBookName(book.getBookName());
        Book saved = repository.save(savedBook);
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
            Model model
    ) {
        Book book = new Book();
        Author author = new Author();
        Style style = new Style();
        author.setAuthorName(authorName);
        style.setStyleName(styleName);
        book.setAuthor(author);
        book.setStyle(style);
        book.setBookName(bookName);
        Book saved = repository.save(book);
        model.addAttribute(saved);
        return "redirect:/";
    }
}
