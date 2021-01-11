package com.books.books.dto;

public class BookDTO {

    public BookDTO(String id, String name, String author, String style) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.style = style;
    }
    private String id;

    //Название книги
    private String name;

    //автор книги
    private String author;

    //жанр книги
    private String style;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }
}
