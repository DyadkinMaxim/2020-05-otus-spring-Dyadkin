package com.books.books.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Book book;
}
