package com.books.books.dto;

import com.books.books.models.Author;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDTO {

    private long id;
    private String authorName;

    public static AuthorDTO toDTO(Author author) {
        return new AuthorDTO(
                author.getId(),
                author.getAuthorName()
        );
    }
}
