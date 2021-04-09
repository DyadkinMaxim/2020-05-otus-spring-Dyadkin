package com.books.books.converters;

import com.books.books.dto.AuthorDTO;
import com.books.books.models.Author;
import org.springframework.stereotype.Service;

@Service
public class AuthorConverterImpl implements AuthorConverter{

    public AuthorDTO toDTO(Author author) {
        return new AuthorDTO(
                author.getId(),
                author.getAuthorName()
        );
    }
}
