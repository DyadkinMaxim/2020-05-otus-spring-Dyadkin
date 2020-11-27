package com.books.books.service;


import com.books.books.models.Book;

public interface BookService {

    void printBooks();

    void printBookById();

    void save();

    void update();

    void delete();

    void printBookInConsole(Book book);
}
