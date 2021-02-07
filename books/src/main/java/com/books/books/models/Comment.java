package com.books.books.models;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document("comments")
public class Comment {

    @Id
    private long id;

    private String commentText;

    private Book book;

    @Override
    public String toString(){
        return "Comment{" +
                "ID: "+ id +
                "commentText: " + commentText +
                "Book: " + book.getBookName() +
                "}";
    }
}
