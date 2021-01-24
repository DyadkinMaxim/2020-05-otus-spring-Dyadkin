package com.books.books.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("authors")
public class Author {

    @Id
    private long id;

    @Column(name = "author", nullable = false)
    private String authorName;

    @OneToMany(targetEntity = Book.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "author")
    private List<Book> authorBooks;

    @Override
    public String toString(){
        return "Author{" +
                "ID: "+ id +
                "authorName: " + authorName +
                "authorBooks: " + authorBooks +
                "}";
    }
}
