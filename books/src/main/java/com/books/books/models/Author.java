package com.books.books.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("authors")
public class Author {

    public Author(String authorName){
        this.authorName = authorName;
    }

    @Id
    private long id;

    private String authorName;

    @DBRef
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
