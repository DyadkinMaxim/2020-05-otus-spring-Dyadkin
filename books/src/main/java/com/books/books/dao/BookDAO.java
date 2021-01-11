package com.books.books.dao;

import com.books.books.dto.BookDTO;
import org.springframework.lang.Nullable;

import java.util.List;

public interface BookDAO {

    List<BookDTO> getAllBooks();

    @Nullable
    BookDTO getBookById(long id);

    Long addBook(BookDTO bookDTO);

    void deleteBook(long id);

    void updateBook(BookDTO bookDTO, long id);
}
