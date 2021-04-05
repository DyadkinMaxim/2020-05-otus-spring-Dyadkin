package com.books.books.converters;

import com.books.books.dto.BookDTO;
import com.books.books.models.Book;

public interface BookConverter {

    BookDTO toDTO(Book book);

    Book toBook(BookDTO bookDTO);
}
