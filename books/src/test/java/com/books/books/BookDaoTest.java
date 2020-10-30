package com.books.books;

import com.books.books.dao.AuthorDAOImpl;
import com.books.books.dao.BookDAOImpl;
import com.books.books.dao.StyleDAOImpl;
import com.books.books.dto.BookDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("DAO для работы с книгами: ")
@JdbcTest
@Import({BookDAOImpl.class, AuthorDAOImpl.class, StyleDAOImpl.class})
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class BookDaoTest {

    @Autowired
    private BookDAOImpl bookDAOImpl;

    @Test
    @DisplayName("выбор всех книг")
    public void selectAllBooks() {
        List<BookDTO> books = bookDAOImpl.getAllBooks();
        final long resultRows = books.size();
        assertThat(resultRows).isEqualTo(5L);
    }

    @Test
    @DisplayName("выбор книги по ID")
    public void selectBookByID() {
        final long bookID = 1L;
        BookDTO bookByName = bookDAOImpl.getBookById(bookID);
        assertThat(bookByName).hasFieldOrPropertyWithValue("name", "Белый клык");
        assertThat(bookByName).hasFieldOrPropertyWithValue("author", "Джек Лондон");
        assertThat(bookByName).hasFieldOrPropertyWithValue("style", "приключения");
    }

    @Test
    @DisplayName("добавлнение новой книги")
    public void addBook() {
        final BookDTO newBook = new BookDTO(null, "Властелин колец", "Джон Толкин", "роман");
        long bookId = bookDAOImpl.addBook(newBook);
        BookDTO actual = bookDAOImpl.getBookById(bookId);
        assertThat(actual.getName()).isEqualTo(newBook.getName());
        assertThat(actual.getAuthor()).isEqualTo(newBook.getAuthor());
        assertThat(actual.getStyle()).isEqualTo(newBook.getStyle());
    }

    @Test
    @DisplayName("удаление книги")
    public void deleteBook() throws SQLException {
       long bookId = 5L;
       bookDAOImpl.deleteBook(bookId);
       BookDTO actual = bookDAOImpl.getBookById(bookId);
       assertThat(actual).isNull();
    }


}
