package com.books.books.service;

import com.books.books.dao.AuthorDAOImpl;
import com.books.books.dao.BookDAOImpl;
import com.books.books.dto.AuthorDTO;
import com.books.books.dto.BookDTO;
import com.books.books.dto.StyleDTO;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@Service
@ShellComponent
public class AuthorServiceImpl implements AuthorService{

    private final AuthorDAOImpl authorDAO;

    public AuthorServiceImpl(AuthorDAOImpl authorDAO) {
        this.authorDAO = authorDAO;
    }

    @Override
    @ShellMethod(value = "Print all authors", key = {"a1"})
    public void printAuthors() {
        List<AuthorDTO> authors =  authorDAO.getAuthors();
        for (AuthorDTO authorDTO : authors) {
            String bookText = authorDTO.getName();
            System.out.println(bookText);
        }
    }

    @Override
    @ShellMethod(value = "Print books by author", key = {"a2"})
    public void printAuthorBooks() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите автора: ");
        String authorName = scanner.nextLine();
        List<BookDTO> booksByAuthor =  authorDAO.getAuthorBooks(authorName);
        for (BookDTO bookDTO : booksByAuthor) {
            String bookText =  " Название: " + bookDTO.getName() + "; \n Автор: " + bookDTO.getAuthor()+ "; \n Жанр:" + bookDTO.getStyle();
            System.out.println(bookText);
        }
    }

    @Override
    @ShellMethod(value = "Print styles by author", key = {"a3"})
    public void printAuthorStyles() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите автора: ");
        String authorName = scanner.nextLine();
        List<StyleDTO> stylesByAuthor =  authorDAO.getAuthorStyles(authorName);
        for (StyleDTO styleDTO : stylesByAuthor) {
            String styleText = styleDTO.getName();
            System.out.println(styleText);
        }
    }
}
