package com.books.books.dto;

import com.books.books.models.Author;
import com.books.books.models.Book;
import com.books.books.models.Comment;
import com.books.books.models.Style;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public static BookDTO toDTO(Book book) {
        List<String> comments = new ArrayList<>();
        for (Comment comment : book.getComment()) {
            comments.add(comment.getCommentText());
        }
        return new BookDTO(book.getId(),
                book.getBookName(),
                book.getAuthor().getAuthorName(),
                book.getStyle().getStyleName(),
                comments);
    }

    public static Book toBook(BookDTO bookDTO) {
        Author author = new Author();
        author.setAuthorName(bookDTO.getAuthorName());
        Style style = new Style();
        style.setStyleName(bookDTO.getStyleName());
        List<Comment> comments = new ArrayList<>();
        if(Objects.nonNull(bookDTO.getComments())) {
            for (String commentText : bookDTO.getComments()) {
                Comment comment = new Comment();
                comment.setCommentText(commentText);
                comments.add(comment);
            }
        }
        return new Book(bookDTO.getId(),
                bookDTO.getBookName(),
                author,
                style,
                comments);
    }
}
