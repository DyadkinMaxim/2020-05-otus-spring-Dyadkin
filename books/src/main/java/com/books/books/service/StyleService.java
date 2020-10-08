package com.books.books.service;

import com.books.books.dto.AuthorDTO;
import com.books.books.dto.BookDTO;
import com.books.books.dto.StyleDTO;

import java.util.List;

public interface StyleService {
    void printStyles();

    void printBooksByStyle();

    void printAuthorsByStyle();
}
