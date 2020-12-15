package com.books.books.service;

import com.books.books.models.Author;
import com.books.books.models.Book;
import com.books.books.models.Comment;
import com.books.books.models.Style;
import com.books.books.repositories.StyleRepositoryJpa;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Service
@ShellComponent
public class StyleServiceImpl implements StyleService {

    private final StyleRepositoryJpa styleRepository;
    private final BookService bookService;

    public StyleServiceImpl(StyleRepositoryJpa styleRepository, BookService bookService) {
        this.styleRepository = styleRepository;
        this.bookService = bookService;
    }

    @Override
    @ShellMethod(value = "Print all styles", key = {"s1"})
    public void printStyles() {
        List<Style> styles = styleRepository.findAll();
        for (Style style : styles) {
            String styleText = " ID: " + style.getId() + "; \n Жанр: " + style.getStyleName();
            System.out.println(styleText);
        }
    }

    @Override
    @ShellMethod(value = "Print style by id", key = {"s2"})
    public void printStyleById() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ID жанра: ");
        long styleId;
        try {
            styleId = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Неверный id. Проверьте введенные данные");
            return;
        }
        Style style = styleRepository.findById(styleId).orElse(new Style());
        if (style.getId() != 0) {
            String styleText = " ID: " + style.getId() + "; \n Жанр: " + style.getStyleName();
            System.out.println(styleText);
        } else {
            System.out.println("Не найден жанр с id: " + styleId);
        }
    }

    @Override
    @ShellMethod(value = "Add new style", key = {"s3"})
    public void save() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите название жанра: ");
        String styleName = scanner.nextLine();
        if (styleName.isEmpty()) {
            System.out.println("Жанр не может быть пустым");
            return;
        }
        if (!Objects.equals(styleRepository.findByName(styleName), null)) {
            System.out.println("Такой жанр уже существует");
            return;
        }
        Style style = new Style();
        style.setStyleName(styleName);
        long styleId = styleRepository.save(style).orElse(0L);
        if (styleId != 0) {
            Style newStyle = styleRepository.findById(styleId).orElse(new Style());
            System.out.println("Добавлен жанр: \n" +
                    " ID: " + newStyle.getId() + "; \n Жанр: " + newStyle.getStyleName());
        }
    }

    @Override
    @Transactional
    @ShellMethod(value = "Update style", key = {"s4"})
    public void update() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ID изменяемого жанра: ");
        long styleId;
        try {
            styleId = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Неверный id. Проверьте введенные данные");
            return;
        }
        System.out.println("Введите название жанра: ");
        String styleName = scanner.nextLine();
        if (styleName.isEmpty()) {
            System.out.println("Жанр не может быть пустым");
            return;
        }
        Style updatedStyle = styleRepository.findById(styleId).orElse(new Style());
        if (updatedStyle.getId() != 0) {
            updatedStyle.setStyleName(styleName);
            Style newStyle = styleRepository.findById(styleId).orElse(new Style());
            System.out.println("Изменен жанр: \n" +
                    " ID: " + newStyle.getId() + "; \n Жанр: " + newStyle.getStyleName());
        } else {
            System.out.println("Не найден жанр с id: " + styleId);
        }
    }

    @Override
    @ShellMethod(value = "Delete style", key = {"s5"})
    public void delete() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ID удаляемого жанра: ");
        long styleId;
        try {
            styleId = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Неверный id. Проверьте введенные данные");
            return;
        }
        int resultSuccess = styleRepository.deleteById(styleId);
        if (resultSuccess != 0) {
            System.out.println("Удален жанр с Id : " + styleId);
        } else {
            System.out.println("Не найден жанр с id: " + styleId);
        }
    }

    @Override
    @ShellMethod(value = "Print books by style", key = {"s6"})
    public void printBooksByStyle() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите жанр: ");
        String styleName = scanner.nextLine();
        if (styleName.isEmpty()) {
            System.out.println("Жанр не может быть пустым");
            return;
        }
        List<Book> booksByStyle = styleRepository.findBooksByStyle(styleName);
        if (!booksByStyle.isEmpty()) {
            for (Book book : booksByStyle) {
                bookService.printBookInConsole(book);
            }
        } else {
            System.out.println("Не найдено книг по введенному жанру");
        }
    }

    @Override
    @ShellMethod(value = "Print authors by style", key = {"s7"})
    public void printAuthorsByStyle() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите жанр: ");
        String styleName = scanner.nextLine();
        if (styleName.isEmpty()) {
            System.out.println("Имя автора не может быть пустым");
            return;
        }
        List<Author> authorsByStyle = styleRepository.findAuthorsByStyle(styleName);
        if (!authorsByStyle.isEmpty()) {
            for (Author author : authorsByStyle) {
                String authorText = author.getAuthorName();
                System.out.println(authorText);
            }
        } else {
            System.out.println("Не найдено авторов по введенному жанру");
        }
    }

}
