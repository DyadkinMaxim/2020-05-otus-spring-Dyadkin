package com.books.books.service;

import com.books.books.dao.AuthorDAOImpl;
import com.books.books.dao.StyleDAOImpl;
import com.books.books.dto.AuthorDTO;
import com.books.books.dto.BookDTO;
import com.books.books.dto.StyleDTO;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.stereotype.Service;

import javax.swing.text.Style;
import java.util.List;
import java.util.Scanner;

@Service
@ShellComponent
public class StyleServiceImpl implements StyleService {

    private final StyleDAOImpl styleDAO;

    public StyleServiceImpl(StyleDAOImpl styleDAO) {
        this.styleDAO = styleDAO;
    }

    @Override
    @ShellMethod(value = "Print styles", key = {"s1"})
    public void printStyles() {
        List<StyleDTO> styles =  styleDAO.getStyles();
        for (StyleDTO styleDTO : styles) {
            String styleText =  "Имя: " + styleDTO.getName();
            System.out.println(styleText);
        }
    }

    @Override
    @ShellMethod(value = "Print books by style", key = {"s2"})
    public void printBooksByStyle() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите жанр: ");
        String styleName = scanner.nextLine();
        List<BookDTO> booksByStyle =  styleDAO.getBooksByStyle(styleName);
        for (BookDTO bookDTO : booksByStyle) {
            String bookText =  "Название: " + bookDTO.getName() + "; \n Автор: " + bookDTO.getAuthor()+ "; \n Жанр:" + bookDTO.getStyle();
            System.out.println(bookText);
        }
    }

    @Override
    @ShellMethod(value = "Print authors by style", key = {"s3"})
    public void printAuthorsByStyle() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите жанр: ");
        String styleName = scanner.nextLine();
        List<AuthorDTO> authorsByStyle =  styleDAO.getAuthorsByStyle(styleName);
        for (AuthorDTO authorDTO : authorsByStyle) {
            String authorText = authorDTO.getName();
            System.out.println(authorText);
        }
    }

}
