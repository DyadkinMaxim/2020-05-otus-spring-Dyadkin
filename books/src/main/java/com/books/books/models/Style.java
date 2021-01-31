package com.books.books.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("styles")
public class Style {

    public Style(String styleName) {
        this.styleName = styleName;
    }

    @Id
    private long id;

    @Column(name = "style", nullable = false)
    private String styleName;

    @OneToMany(targetEntity = Book.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "style")
    @DBRef
    private List<Book> styleBooks;

    @Override
    public String toString(){
        return "Style{" +
                "ID: "+ id +
                "styleName: " + styleName +
                "}";
    }
}
