package com.books.books.rest;

import com.books.books.MVCservice.CommentService;
import com.books.books.models.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/saveComment")
    public String toSave(Model model) {
        Comment comment = new Comment();
        model.addAttribute(comment);
        return "saveComment";
    }

    @PostMapping("/saveComment")
    @ExceptionHandler(NotFoundException.class)
    public String saveComment(
            Comment comment,
            long bookId
    ) {
        commentService.saveComment(comment, bookId);
        return "redirect:/";
    }
}
