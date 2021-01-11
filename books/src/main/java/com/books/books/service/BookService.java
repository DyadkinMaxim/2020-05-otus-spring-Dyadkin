package com.books.books.service;


import com.books.books.models.Book;

import java.util.List;

public interface BookService {

    void printBooks();

    void printBookById();

    void save();

    void update();

    void delete();

    void printAllBooksInConsole(List<Book> books);

    void printBookInConsole(Book book);
}
