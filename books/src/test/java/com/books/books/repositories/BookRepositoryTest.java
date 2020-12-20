package com.books.books.repositories;

import com.books.books.models.Author;
import com.books.books.models.Book;
import com.books.books.models.Comment;
import com.books.books.models.Style;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;




@DisplayName("Репозиторий на основе Jpa для работы с книгами ")
@DataJpaTest
@Import(BookRepositoryJpaImpl.class)
class BookRepositoryTest {

    private static final int EXPECTED_NUMBER_OF_BOOKS = 5;
    private static final long FIRST_BOOK_ID = 1L;
    private static final String FIRST_BOOK_NAME = "Белый клык";
    private static final String NEW_BOOK_STYLE = "newStyle";
    private static final String NEW_BOOK_AUTHOR = "newAuthor";
    private static final String NEW_BOOK_COMMENT = "newComment";
    private static final String NEW_BOOK_NAME = "newBookName";
    private static final String UPDATED_BOOK_NAME = "updatedBookName";

    @Autowired
    private BookRepositoryJpaImpl bookRepository;

    @Autowired
    private TestEntityManager em;

    @DisplayName(" должен загружать информацию о нужной книге по ее id")
    @Test
    void shouldFindExpectedBookById() {
        val optionalActualBook = bookRepository.findById(FIRST_BOOK_ID);
        val expectedBook = em.find(Book.class, FIRST_BOOK_ID);
        assertThat(optionalActualBook).isPresent().get()
                .isEqualToComparingFieldByField(expectedBook);
    }

    @DisplayName("должен загружать список всех книг с полной информацией о них")
    @Test
    void shouldReturnCorrectBookListWithAllInfo() {

        val books = bookRepository.findAll();
        assertThat(books).isNotNull().hasSize(EXPECTED_NUMBER_OF_BOOKS);
    }

    @DisplayName(" должен корректно сохранять всю информацию о книге")
    @Test
    void shouldSaveAllBookInfo() {
        val style = new Style(0, NEW_BOOK_STYLE);
        val author = new Author(0, NEW_BOOK_AUTHOR);
        val newBook = new Book(0, NEW_BOOK_NAME, author, style, null);
        val comment = new Comment(0, NEW_BOOK_COMMENT, newBook);
        val comments = Collections.singletonList(comment);
        newBook.setComment(comments);

        bookRepository.save(newBook);
        assertThat(newBook.getId()).isGreaterThan(0);

        val actualBook = em.find(Book.class, newBook.getId());
        assertThat(actualBook).isNotNull().matches(b -> !b.getBookName().equals(""))
                .matches(b -> b.getAuthor() != null)
                .matches(b -> b.getStyle() != null)
                .matches(b -> b.getComment() != null && b.getComment().size() > 0);
    }

    @DisplayName(" должен загружать информацию о нужной книге по ее названию")
    @Test
    void shouldFindExpectedBookByName() {
        val firstBook = em.find(Book.class, FIRST_BOOK_ID);
        Book actualBook = bookRepository.findByName(FIRST_BOOK_NAME);
        assertThat(actualBook).isEqualToComparingFieldByField(firstBook);
    }

    @DisplayName(" должен удалять заданную книгу по ее id")
    @Test
    void shouldDeleteBook() {
        val firstBook = em.find(Book.class, FIRST_BOOK_ID);
        assertThat(firstBook).isNotNull();
        em.detach(firstBook);

        bookRepository.deleteById(FIRST_BOOK_ID);
        val deletedBook = em.find(Book.class, FIRST_BOOK_ID);

        assertThat(deletedBook).isNull();
    }
}
