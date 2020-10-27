package com.books.books.service;

import com.books.books.dao.BookDAOImpl;
import com.books.books.dto.BookDTO;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@Service
@ShellComponent
public class BookServiceImpl implements BookService {

    private final BookDAOImpl bookDAO;

    public BookServiceImpl(BookDAOImpl bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    @ShellMethod(value = "Print all books", key = {"b1"})
    public void printBooks() {
       List<BookDTO> books =  bookDAO.getAllBooks();
        for (BookDTO bookDTO : books) {
            String bookText =  " ID: " + bookDTO.getId() + "; \n Название: " + bookDTO.getName() + "; \n Автор: " + bookDTO.getAuthor()+ "; \n Жанр:" + bookDTO.getStyle();
            System.out.println(bookText);

            }
        }

    @Override
    @ShellMethod(value = "Print books by id", key = {"b2"})
    public void printBookByName() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ID книги: ");
        long bookId = scanner.nextLong();
        BookDTO bookByName =  bookDAO.getBookById(bookId);
            String bookText =  " ID: " + bookByName.getId() + "; \n Название: " + bookByName.getName() + "; \n Автор: " + bookByName.getAuthor()+ "; \n Жанр:" + bookByName.getStyle();
            System.out.println(bookText);
    }

    @Override
    @ShellMethod(value = "Add new book", key = {"b3"})
    public void addBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите название книги: ");
        String bookName = scanner.nextLine();
        System.out.println("Введите автора книги: ");
        String bookAuthor = scanner.nextLine();
        System.out.println("Введите Жанр книги: ");
        String bookStyle = scanner.nextLine();
        BookDTO bookDTO = new BookDTO(null, bookName, bookAuthor, bookStyle);
        Long bookId = bookDAO.addBook(bookDTO);
        System.out.println("Добавлена книга с id: " + bookId);
    }

    @Override
    @ShellMethod(value = "Update book", key = {"b4"})
    public void updateBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ID изменяемой книги: ");
        String bookID = scanner.nextLine();
        System.out.println("Введите название книги: ");
        String bookName = scanner.nextLine();
        System.out.println("Введите автора книги: ");
        String bookAuthor = scanner.nextLine();
        System.out.println("Введите Жанр книги: ");
        String bookStyle = scanner.nextLine();
        BookDTO bookDTO = new BookDTO(bookID, bookName, bookAuthor, bookStyle);
        bookDAO.updateBook(bookDTO, Long.valueOf(bookID));
    }

    @Override
    @ShellMethod(value = "Delete book", key = {"b5"})
    public void deleteBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ID удаляемой книги: ");
        long bookID = scanner.nextLong();
        bookDAO.deleteBook(bookID);
        System.out.println("Удалена книга с ID : " + bookID);
    }
}
