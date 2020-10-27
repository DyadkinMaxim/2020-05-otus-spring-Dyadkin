package com.books.books.dao;

import com.books.books.dto.AuthorDTO;
import com.books.books.dto.BookDTO;
import com.books.books.dto.StyleDTO;

import java.util.List;

public interface AuthorDAO {

    Long addAuthor(String name);

    Long getAuthorByName(String name);

    List<AuthorDTO> getAuthors();

    List<BookDTO> getAuthorBooks(String name);

    List<StyleDTO> getAuthorStyles(String name);
}
