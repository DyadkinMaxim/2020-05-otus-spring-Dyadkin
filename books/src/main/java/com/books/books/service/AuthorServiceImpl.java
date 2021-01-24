package com.books.books.service;

import com.books.books.models.Author;
import com.books.books.models.Book;
import com.books.books.mongoRepos.AuthorRepository;
import com.books.books.mongoRepos.BookRepository;
import com.books.books.mongoRepos.StyleRepository;
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

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final StyleRepository styleRepository;
    private final BookService bookService;

    public AuthorServiceImpl(AuthorRepository authorRepository,
                             BookRepository bookRepository,
                             StyleRepository styleRepository,
                             BookService bookService) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.styleRepository = styleRepository;
        this.bookService = bookService;
    }

    @Override
    @Transactional
    @ShellMethod(value = "Print all authors", key = {"a1"})
    public void printAuthors() {
        List<Author> authors = (List<Author>) authorRepository.findAll();
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
        if (!Objects.equals(authorRepository.findByAuthorNameContains(authorName), null)) {
            System.out.println("Такой автор уже добавлен");
            return;
        }
        Author author = new Author();
        author.setAuthorName(authorName);
      authorRepository.save(author);
      Author savedAuthor = authorRepository.findById(author.getId()).orElse(new Author());
        if (savedAuthor.getId() != 0) {
            Author newAuthor = authorRepository.findById(author.getId()).orElse(new Author());
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
        Author updatedAuthor = authorRepository.findById(authorId).orElse(new Author());
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
        Author author = authorRepository.findById(authorId).orElse(new Author());
        if (!(author.getId() == 0)) {
            if(author.getAuthorBooks().isEmpty()) {
                authorRepository.deleteById(authorId);
            }
            else{
                System.out.println("Удалить автора невозможно - сначала удалите все книги этого автора");
            }
        } else {
            System.out.println("Не найдено автора по id: " + authorId);
        }
    }

    @Override
    @Transactional
    @ShellMethod(value = "Print books by author", key = {"a6"})
    public void printBooksByAuthor() {
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
        if (!Objects.equals(author, null)) {
            bookService.printAllBooksInConsole(author.getAuthorBooks());
        } else {
            System.out.println("Не найдено книг по введенному автору");
        }
    }


    @Override
    @Transactional
    @ShellMethod(value = "Print styles by author", key = {"a7"})
    public void printStylesByAuthor() {
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
        if (!Objects.equals(author, null)) {
            for (Book authorBook : author.getAuthorBooks()) {
                String styleText = authorBook.getStyle().getStyleName();
                System.out.println(styleText);
            }
        } else {
            System.out.println("Не найдено жанров по введенному автору");
        }
    }
}
