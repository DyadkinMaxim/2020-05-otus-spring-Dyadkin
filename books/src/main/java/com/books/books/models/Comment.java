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

    @Basic(fetch = FetchType.LAZY)
    @Column(name = "comment", nullable = false)
    private String commentText;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn
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
