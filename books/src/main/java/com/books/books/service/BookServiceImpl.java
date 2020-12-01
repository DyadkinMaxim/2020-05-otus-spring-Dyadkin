package com.books.books.service;

import com.books.books.models.Author;
import com.books.books.models.Book;
import com.books.books.models.Comment;
import com.books.books.models.Style;
import com.books.books.repositories.BookRepositoryJpa;
import com.books.books.repositories.CommentRepositoryJpa;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Service
@ShellComponent
public class BookServiceImpl implements BookService {

    @PersistenceContext
    EntityManager em;

    private final BookRepositoryJpa bookRepository;
    private final CommentRepositoryJpa commentRepository;

    public BookServiceImpl(BookRepositoryJpa bookRepository, CommentRepositoryJpa commentRepository) {
        this.bookRepository = bookRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    @ShellMethod(value = "Print all books", key = {"b1"})
    public void printBooks() {
        List<Book> books = bookRepository.findAll();
        for (Book book : books) {
            printBookInConsole(book);
        }
    }

    @Override
    @ShellMethod(value = "Print book by id", key = {"b2"})
    public void printBookById() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ID книги: ");
        long bookId;
        try {
            bookId = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Неверный id. Проверьте введенные данные");
            return;
        }
        Book book = bookRepository.findById(bookId).orElse(new Book());
        if (book.getId() != 0) {
            printBookInConsole(book);
        } else {
            System.out.println("Не найдена книга с id: " + bookId);
        }
    }

    @Override
    @ShellMethod(value = "Add new book", key = {"b3"})
    public void save() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите название книги: ");
        String bookName = scanner.nextLine();
        if (bookName.isEmpty()) {
            System.out.println("Название книги не может быть пустым");
            return;
        }
        System.out.println("Введите автора книги: ");
        String bookAuthorText = scanner.nextLine();
        Author bookAuthor = new Author();
        if (!bookAuthorText.isEmpty()) {
            bookAuthor.setAuthorName(bookAuthorText);
        } else {
            System.out.println("Имя автора книги не может быть пустым");
            return;
        }
        System.out.println("Введите жанр книги: ");
        String bookStyleText = scanner.nextLine();
        Style bookStyle = new Style();
        if (!bookStyleText.isEmpty()) {
            bookStyle.setStyleName(bookStyleText);
        } else {
            System.out.println("Название жанра книги не может быть пустым");
            return;
        }
        List<Comment> bookComments = new ArrayList<>();
        boolean isNextComment = true;
        int counter = 0;
        while (isNextComment) {
            if (counter == 0) {
                System.out.println("Ввести комментарий к книге(y - да, n - нет)?: ");
            } else {
                System.out.println("Ввести еще один комментарий к книге(y - да, n - нет)?: ");
            }
            String nextComment = scanner.nextLine();
            if (Objects.equals(nextComment, "y")) {
                System.out.println("Введите комментарий к книге: ");
                String bookCommentText = scanner.nextLine();
                if (!bookCommentText.isEmpty()) {
                    Comment bookComment = new Comment();
                    bookComment.setCommentText(bookCommentText);
                    bookComments.add(bookComment);
                } else {
                    System.out.println("Комментарий к книге не может быть пустым");
                }
                counter++;
                continue;
            }
            if (Objects.equals(nextComment, "n")) {
                isNextComment = false;
            } else {
                System.out.println("Введено неверное значение. Пожалуйста, проверьте ввод.");
                continue;
            }
            counter++;
        }
        Book book = new Book(0, bookName, bookAuthor, bookStyle, null);
        Book saveBook = bookRepository.save(book).orElse(new Book());
        long bookId = saveBook.getId();
        if (bookId != 0) {
            for (Comment comment : bookComments) {
                comment.setBookId(bookId);
                commentRepository.save(comment);
            }
            Book newBook = bookRepository.findById(bookId).orElse(new Book());
            System.out.println("Добавлена книга: ");
            printBookInConsole(newBook);
        }
    }

    @Override
    @ShellMethod(value = "Update book", key = {"b4"})
    @Transactional
    public void update() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ID изменяемой книги: ");
        long bookId;
        try {
            bookId = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Неверный id. Проверьте введенные данные");
            return;
        }
        System.out.println("Введите название книги: ");
        String bookName = scanner.nextLine();
        if (bookName.isEmpty()) {
            System.out.println("Название книги не может быть пустым");
            return;
        }
        Book book = bookRepository.findById(bookId).orElse(new Book());
        if (book.getId() != 0) {
            em.detach(book);
            book.setBookName(bookName);
            bookRepository.save(book);
            if (book.getId() != 0) {
                System.out.println("Изменена книга: ");
                printBookInConsole(book);
            }
        }
        else {
            System.out.println("Не найдена книга с id: " + book.getId());
        }
    }

    @Override
    @ShellMethod(value = "Delete book", key = {"b5"})
    public void delete() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ID удаляемой книги: ");
        long bookId;
        try {
            bookId = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Неверный id. Проверьте введенные данные");
            return;
        }
        int resultSuccess = bookRepository.deleteById(bookId);
        if (resultSuccess != 0) {
            System.out.println("Удалена книга с Id : " + bookId);
        } else {
            System.out.println("Не найдена книга с id: " + bookId);
        }
    }

    public void printBookInConsole(Book book) {
        List<String> bookText = new ArrayList<>();
        bookText.add("ID: " + book.getId() +
                "; \n Название: " + book.getBookName() +
                "; \n Автор: " + book.getAuthor().getAuthorName() +
                "; \n Жанр:" + book.getStyle().getStyleName());
        if (!book.getComment().isEmpty()) {
            int i = 0;
            for (Comment bookComment : book.getComment()) {
                i++;
                bookText.add(" Комментарий " + i + ": " + bookComment.getCommentText());
            }
        }
        for (String bookForPrint : bookText) {
            System.out.println(bookForPrint);
        }
    }
}