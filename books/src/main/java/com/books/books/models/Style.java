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
@Document("styles")
public class Style {

    @Id
    private long id;

    @Column(name = "style", nullable = false)
    private String styleName;

    @OneToMany(targetEntity = Book.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "style")
    private List<Book> styleBooks;

    @Override
    public String toString(){
        return "Style{" +
                "ID: "+ id +
                "styleName: " + styleName +
                "styleBooks: " + styleBooks +
                "}";
    }
}
