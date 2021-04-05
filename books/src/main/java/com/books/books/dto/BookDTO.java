package com.books.books.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

    private long id = -1;
    private String bookName;
    private String authorName;
    private String styleName;
    private List<String> comments;
}
