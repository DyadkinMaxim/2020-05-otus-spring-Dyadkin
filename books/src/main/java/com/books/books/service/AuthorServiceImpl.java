package com.books.books.service;

import com.books.books.models.Author;
import com.books.books.models.Book;
import com.books.books.models.Style;
import com.books.books.repositories.AuthorRepositoryJpa;
import com.books.books.repositories.BookRepositoryJpa;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Service
@ShellComponent
public class AuthorServiceImpl implements AuthorService {

    @PersistenceContext
    EntityManager em;

    private final AuthorRepositoryJpa authorRepository;
    private final BookRepositoryJpa bookRepository;
    private final BookService bookService;

    public AuthorServiceImpl(AuthorRepositoryJpa authorRepository, BookRepositoryJpa bookRepository, BookService bookService) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.bookService = bookService;
    }

    @Override
    @Transactional
    @ShellMethod(value = "Print all authors", key = {"a1"})
    public void printAuthors() {
        List<Author> authors = authorRepository.findAll();
        for (Author author : authors) {
            String authorText = " ID: " + author.getId() + "; \n Автор: " + author.getAuthorName();
            System.out.println(authorText);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @ShellMethod(value = "Print author by id", key = {"a2"})
    public void printAuthorById() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ID автора: ");
        long authorId;
        try {
            authorId = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Неверный id. Проверьте введенные данные");
            return;
        }
        Author author = authorRepository.findById(authorId).orElse(new Author());
        if (author.getId() != 0) {
            String authorText = " ID: " + author.getId() + "; \n Автор: " + author.getAuthorName();
            System.out.println(authorText);
        } else {
            System.out.println("Не найден автор с id: " + authorId);
        }
    }

    @Override
    @Transactional
    @ShellMethod(value = "Add new author", key = {"a3"})
    public void save() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите имя автора: ");
        String authorName = scanner.nextLine();
        if (authorName.isEmpty()) {
            System.out.println("Имя автора не может быть пустым");
            return;
        }
        if (!Objects.equals(authorRepository.findByName(authorName), null)) {
            System.out.println("Такой автор уже добавлен");
            return;
        }
        Author author = new Author();
        author.setAuthorName(authorName);
        long authorId = authorRepository.save(author).orElse(0L);
        if (authorId != 0) {
            Author newAuthor = authorRepository.findById(authorId).orElse(new Author());
            System.out.println("Добавлен автор: \n" +
                    " ID: " + newAuthor.getId() + "; \n Автор: " + newAuthor.getAuthorName());
        }
    }

    @Override
    @Transactional
    @ShellMethod(value = "Update author", key = {"a4"})
    public void update() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ID редактируемого автора: ");
        long authorId;
        try {
            authorId = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Неверный id. Проверьте введенные данные");
            return;
        }
        System.out.println("Введите название автора: ");
        String authorName = scanner.nextLine();
        if (authorName.isEmpty()) {
            System.out.println("Автор не может быть пустым");
            return;
        }
        Author  updatedAuthor = authorRepository.findById(authorId).orElse(new Author());
        if (updatedAuthor.getId() != 0) {
            updatedAuthor.setAuthorName(authorName);
            Author newAuthor = authorRepository.findById(authorId).orElse(new Author());
            System.out.println("Изменен автор: \n" +
                    " ID: " + newAuthor.getId() + "; \n Автор: " + newAuthor.getAuthorName());
        } else {
            System.out.println("Не найден автор с id: " + authorId);
        }
    }

    @Override
    @Transactional
    @ShellMethod(value = "Delete author", key = {"a5"})
    public void delete() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ID удаляемого автора: ");
        long authorId;
        try {
            authorId = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Неверный id. Проверьте введенные данные");
            return;
        }
        int resultSuccess = authorRepository.deleteById(authorId);
        if (resultSuccess != 0) {
            System.out.println("Удален автор с Id : " + authorId);
        } else {
            System.out.println("Не найден автор с id: " + authorId);
        }
    }

    @Override
    @Transactional
    @ShellMethod(value = "Print books by author", key = {"a6"})
    public void printBooksByAuthor() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите автора: ");
        String authorName = scanner.nextLine();
        if (authorName.isEmpty()) {
            System.out.println("Имя автора не может быть пустым");
            return;
        }
        List<Book> booksByAuthor = authorRepository.findBooksByAuthor(authorName);
        if (!booksByAuthor.isEmpty()) {
                bookService.printAllBooksInConsole(booksByAuthor);
        } else {
            System.out.println("Не найдено книг по введенному автору");
        }
    }


    @Override
    @Transactional
    @ShellMethod(value = "Print styles by author", key = {"a7"})
    public void printStylesByAuthor() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите автора: ");
        String authorName = scanner.nextLine();
        if (authorName.isEmpty()) {
            System.out.println("Имя автора не может быть пустым");
            return;
        }
        List<Style> stylesByAuthor = authorRepository.findStylesByAuthor(authorName);
        if (!stylesByAuthor.isEmpty()) {
            for (Style style : stylesByAuthor) {
                String styleText = style.getStyleName();
                System.out.println(styleText);
            }
        } else {
            System.out.println("Не найдено жанров по введенному автору");
        }
    }
}
