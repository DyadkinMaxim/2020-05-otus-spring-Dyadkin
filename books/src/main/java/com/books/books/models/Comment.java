package com.books.books.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Basic(fetch = FetchType.LAZY)
    @Column(name = "comment", nullable = false)
    private String commentText;

    @JoinColumn(name = "book_id")
    private long bookId;
}
