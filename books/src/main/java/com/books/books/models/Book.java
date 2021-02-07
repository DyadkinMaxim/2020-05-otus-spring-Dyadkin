package com.books.books.models;


import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document("books")
public class Book {

    @Id
    private long id;

    private String bookName;

    private Author author;

    private Style style;

    @DBRef
    private List<Comment> comment;

    @Override
    public String toString(){
        return "Book{" +
                "ID: "+ id +
                "bookName: " + bookName +
                "Author: " + author.getAuthorName() +
                "Style: " + style.getStyleBooks() +
                "comments: " + comment +
                "}";
    }
}
