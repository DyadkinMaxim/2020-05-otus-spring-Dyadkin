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

    @Column(name = "book", nullable = false)
    private String bookName;

    @ManyToOne(targetEntity = Author.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @ManyToOne(targetEntity = Style.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "style_id", nullable = false)
    private Style style;

    @OneToMany(targetEntity = Comment.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "book")
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
