package com.books.books.service;

import com.books.books.models.Book;
import com.books.books.models.Style;
import com.books.books.repositoriesSpringDataJPA.StyleRepository;
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

    private final StyleRepository styleRepository;
    private final BookService bookService;

    public StyleServiceImpl(StyleRepository styleRepository, BookService bookService) {
        this.styleRepository = styleRepository;
        this.bookService = bookService;
    }

    @Override
    @Transactional
    @ShellMethod(value = "Print all styles", key = {"s1"})
    public void printStyles() {
        List<Style> styles = styleRepository.findAll();
        for (Style style : styles) {
            String styleText = " ID: " + style.getId() + "; \n Жанр: " + style.getStyleName();
            System.out.println(styleText);
        }
    }

    @Override
    @Transactional(readOnly = true)
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
    @Transactional
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
        styleRepository.save(style);
        if (styleRepository.existsById(style.getId())) {
            Style newStyle = styleRepository.findById(style.getId()).orElse(new Style());
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
    @Transactional
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
        Style style = styleRepository.findById(styleId).orElse(new Style());
        if (!(style.getId() == 0)) {
            if(style.getStyleBooks().isEmpty()) {
                styleRepository.deleteById(styleId);
            }
            else{
                System.out.println("Удалить жанр невозможно - сначала удалите все книги этого жанра");
            }
        } else {
            System.out.println("Не найдено жанра по id: " + styleId);
        }

    }

    @Override
    @Transactional
    @ShellMethod(value = "Print books by style", key = {"s6"})
    public void printBooksByStyle() {
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
        if (!Objects.equals(style, null)) {
            bookService.printAllBooksInConsole(style.getStyleBooks());
        } else {
            System.out.println("Не найдено жанров по введенному жанру");
        }
    }

    @Override
    @Transactional
    @ShellMethod(value = "Print authors by style", key = {"s7"})
    public void printAuthorsByStyle() {
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
        if (!Objects.equals(style, null)) {
            for (Book bookByStyle : style.getStyleBooks()) {
                String authorText = bookByStyle.getAuthor().getAuthorName();
                System.out.println(authorText);
            }
        } else {
            System.out.println("Не найдено авторов по введенному жанру");
        }
    }

}
