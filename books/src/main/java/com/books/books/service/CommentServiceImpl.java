package com.books.books.service;


import com.books.books.models.Book;
import com.books.books.models.Comment;
import com.books.books.repositories.BookRepositoryJpa;
import com.books.books.repositories.CommentRepositoryJpa;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@ShellComponent
public class CommentServiceImpl implements CommentService {

    private final CommentRepositoryJpa commentRepository;
    private final BookRepositoryJpa bookRepository;

    public CommentServiceImpl(CommentRepositoryJpa commentRepository, BookRepositoryJpa bookRepository) {
        this.commentRepository = commentRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    @ShellMethod(value = "Print all comments", key = {"c1"})
    public void printComments() {
        List<Comment> comments = commentRepository.findAll();
        for (Comment comment : comments) {
            Book book = bookRepository.findById(comment.getBookId()).orElse(new Book());
            String commentText = " ID: " + comment.getId() + ";" +
                    " \n Комментарий: " + comment.getCommentText() +
                    " \n Книга: " + book.getBookName();
            System.out.println(commentText);
        }
    }

    @Override
    @ShellMethod(value = "Print comment by id", key = {"c2"})
    public void printCommentById() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ID комментария: ");
        long commentId;
        try {
            commentId = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Неверный id. Проверьте введенные данные");
            return;
        }
        Comment comment = commentRepository.findById(commentId).orElse(new Comment());
        if (comment.getId() != 0) {
            String commentText = " ID: " + comment.getId() + "; \n Комментарий: " + comment.getCommentText();
            System.out.println(commentText);
        } else {
            System.out.println("Не найден комментарий с id: " + commentId);
        }
    }

    @Override
    @ShellMethod(value = "Add new comment", key = {"c3"})
    public void save() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите комментарий: ");
        String commentText = scanner.nextLine();
        if (commentText.isEmpty()) {
            System.out.println("Комментарий не может быть пустым");
            return;
        }
        System.out.println("Введите ID книги, к которой принадлежит комментарий: ");
        long bookId;
        try {
            bookId = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Неверный id. Проверьте введенные данные");
            return;
        }
        Book book = bookRepository.findById(bookId).orElse(new Book());
        if (book.getId() == 0L) {
            System.out.println("Не найдено книги с данным id");
            return;
        }
        Comment comment = new Comment();
        comment.setCommentText(commentText);
        comment.setBookId(bookId);
        long commentId = commentRepository.save(comment).orElse(0L);
        if (commentId != 0) {
            Comment newComment = commentRepository.findById(commentId).orElse(new Comment());
            System.out.println("Добавлен комментарий: \n" +
                    " ID: " + newComment.getId() + "; \n Комментарий: " + newComment.getCommentText());
        }
    }

    @Override
    @ShellMethod(value = "Update comment", key = {"c4"})
    public void update() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ID изменяемого комментария: ");
        long commentId;
        try {
            commentId = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Неверный id. Проверьте введенные данные");
            return;
        }
        System.out.println("Введите комментарий: ");
        String commentText = scanner.nextLine();
        if (commentText.isEmpty()) {
            System.out.println("Комментарий не может быть пустым");
            return;
        }
        Comment comment = new Comment(commentId, commentText, 0);
        int resultSuccess = commentRepository.updateCommentText(comment);
        if (resultSuccess != 0) {
            Comment newComment = commentRepository.findById(commentId).orElse(new Comment());
            System.out.println("Изменен комментарий: \n" +
                    " ID: " + newComment.getId() + "; \n Комментарий: " + newComment.getCommentText());
        } else {
            System.out.println("Не найден комментарий с id: " + comment.getId());
        }
    }

    @Override
    @ShellMethod(value = "Delete comment", key = {"c5"})
    public void delete() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ID удаляемого комментария: ");
        long commentId;
        try {
            commentId = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Неверный id. Проверьте введенные данные");
            return;
        }
        int resultSuccess = commentRepository.deleteById(commentId);
        if (resultSuccess != 0) {
            System.out.println("Удален комментарий с Id : " + commentId);
        } else {
            System.out.println("Не найден комментарий с id: " + commentId);
        }
    }
}
