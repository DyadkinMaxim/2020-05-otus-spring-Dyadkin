package com.books.books.rest;

import com.books.books.models.Book;
import com.books.books.models.Comment;
import com.books.books.repositoriesSpringDataJPA.BookRepository;
import com.books.books.repositoriesSpringDataJPA.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {
    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;
    @Autowired
    public CommentController(CommentRepository commentRepository, BookRepository bookRepository) {
        this.commentRepository = commentRepository;
        this.bookRepository = bookRepository;
    }

    @GetMapping("/saveCommentSingle")
    public String toSave() {
        return "saveComment";
    }

    @PostMapping("/saveCommentSingle")
    @ExceptionHandler(NotFoundException.class)
    public String saveCommentSingle(
            String commentText,
            long bookId,
            Model model
    ) {
        Book book = bookRepository.findById(bookId).orElseThrow(NotFoundException::new);
        Comment comment = new Comment();
        comment.setCommentText(commentText);
        comment.setBook(book);
        commentRepository.save(comment);
        model.addAttribute(comment);
        return "redirect:/";
    }

    @PostMapping("/saveCommentInBook")
    public String saveCommentInBook(
            String commentText,
            Book book,
            Model model
    ) {

        Comment comment = new Comment();
        comment.setCommentText(commentText);
        comment.setBook(book);
        commentRepository.save(comment);
        model.addAttribute(comment);
        return "redirect:/";
    }

}
