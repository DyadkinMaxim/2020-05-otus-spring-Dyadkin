package com.books.books.converters;

import com.books.books.dto.AuthorDTO;
import com.books.books.models.Author;

public interface AuthorConverter {
    AuthorDTO toDTO(Author author);
}
