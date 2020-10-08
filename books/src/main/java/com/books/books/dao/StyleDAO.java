package com.books.books.dao;

import com.books.books.dto.AuthorDTO;
import com.books.books.dto.BookDTO;
import com.books.books.dto.StyleDTO;

import java.util.List;

public interface StyleDAO {

    List<StyleDTO> getStyles();

    List<BookDTO> getBooksByStyle(String name);

    List<AuthorDTO> getAuthorsByStyle(String name);
}
