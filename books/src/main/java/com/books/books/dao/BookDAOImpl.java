package com.books.books.dao;

import com.books.books.dao.BookDAO;
import com.books.books.dto.AuthorDTO;
import com.books.books.dto.BookDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcCallOperations;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class BookDAOImpl implements BookDAO {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private JdbcOperations jdbc;

    @Override
    public List<BookDTO> getAllBooks() {
        final String SELECT_ALL_BOOKS = "SELECT * FROM BOOKS";
        return namedParameterJdbcTemplate.query(SELECT_ALL_BOOKS, new BookMapper());
    }

    @Override
    public BookDTO getBookById(long id){
        final String SELECT_BY_ID = "SELECT * FROM BOOKS WHERE ID = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        try {
            BookDTO bookById = namedParameterJdbcTemplate.queryForObject(SELECT_BY_ID, params, new BookMapper());
            return bookById;
        }
        catch (DataAccessException e) {
            System.out.println("Книги с введенным id не найдено");
            return null;
        }
    }


    @Override
    public long addBook(BookDTO bookDTO) {
        final String SELECT_MAX_ID = "SELECT MAX(ID) FROM BOOKS";
        final long id = jdbc.queryForObject(SELECT_MAX_ID, Long.class) +1L;
        final String ADD_NEW_BOOK = "INSERT INTO BOOKS (id, name, author, style) VALUES (" +
                ":id, " +
                ":name, " +
                ":author, " +
                ":style)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        params.addValue("name", bookDTO.getName());
        params.addValue("author", bookDTO.getAuthor());
        params.addValue("style", bookDTO.getStyle());
        namedParameterJdbcTemplate.update(ADD_NEW_BOOK, params);
        return id;
    }

    @Override
    public void updateBook(BookDTO bookDTO, long id) {
        final String UPDATE_BOOK = "UPDATE BOOKS SET " +
                "name = :name, " +
                "author = :author, " +
                "style = :style " +
                "WHERE ID = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", bookDTO.getName());
        params.addValue("author", bookDTO.getAuthor());
        params.addValue("style", bookDTO.getStyle());
        params.addValue("id", id);
        try {
            namedParameterJdbcTemplate.update(UPDATE_BOOK, params);
        }
        catch(DataAccessException e) {
            System.out.println("Книги с введенным id не найдено");
        }
    }

    @Override
    public void deleteBook(long id) {
        final String DELETE_BOOK = "DELETE FROM BOOKS WHERE ID = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        namedParameterJdbcTemplate.update(DELETE_BOOK, params);
    }

       public static class BookMapper implements RowMapper<BookDTO> {
        @Override
        public BookDTO mapRow(ResultSet rs, int i) throws SQLException {
            final String id = rs.getString("id");
            final String name = rs.getString("name");
            final String author = rs.getString("author");
            final String style = rs.getString("style");
            return new BookDTO(
                    id,
                    name,
                    author,
                    style);
        }
    }
}
