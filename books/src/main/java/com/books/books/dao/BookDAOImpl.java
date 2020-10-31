package com.books.books.dao;

import com.books.books.dto.BookDTO;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookDAOImpl implements BookDAO {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private JdbcTemplate jdbcTemplate;

    private AuthorDAOImpl authorDAO;

    private  StyleDAOImpl styleDAO;

    public BookDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcTemplate jdbcTemplate, AuthorDAOImpl authorDAO, StyleDAOImpl styleDAO) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
        this.authorDAO = authorDAO;
        this.styleDAO = styleDAO;
    }

    @Override
    public List<BookDTO> getAllBooks() {
        final String SELECT_ALL_BOOKS = "SELECT books.id, book, authors.author, styles.style FROM BOOKS " +
                "left join authors on authors.id = books.author_id " +
                "left join styles on styles.id = books.style_id";
        return namedParameterJdbcTemplate.query(SELECT_ALL_BOOKS, new BookMapper());
    }

    @Override
    public BookDTO getBookById(long id){
        final String SELECT_BY_ID = "SELECT books.id, book, authors.author, styles.style FROM BOOKS " +
                "left join authors on authors.id = books.author_id " +
                "left join styles on styles.id = books.style_id " +
                "where books.id = :id";
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
    public Long addBook(BookDTO bookDTO) {

        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
        insert.setGeneratedKeyName("id");
        final Map<String, Object> data = new HashMap<>();
        data.put("book", bookDTO.getName());
        final List<String> columns = new ArrayList();
        columns.add("book");
        columns.add("author_id");
        columns.add("style_id");
        insert.setColumnNames(columns);
        insert.setTableName("books");

        final Long authorById = authorDAO.getAuthorIdByName(bookDTO.getAuthor());
        final Long styleById = styleDAO.getStyleByName(bookDTO.getStyle());

        if(authorById == null) {
            Long authorId = authorDAO.addAuthor(bookDTO.getAuthor());
            data.put("author_id", authorId);
        }
        else {
            data.put("author_id", authorById);
        }
        if(styleById == null) {
            Long styleId = styleDAO.addStyle(bookDTO.getStyle());
            data.put("style_id", styleId);
        }
        else {
            data.put("style_id", styleById);
        }
        final Long bookId = (Long) insert.executeAndReturnKey(data);
        return bookId;
    }


    @Override
    public void updateBook(BookDTO bookDTO, long id) {
        final String UPDATE_BOOK = "UPDATE BOOKS SET " +
                "BOOKS.book = :book " +
                "WHERE books.id = :id";
        final String UPDATE_AUTHOR = "update authors set author = :author " +
                "where author in (select author from authors " +
                "left join books on books.author_id = authors.id " +
                "where books.id = :id)";
        final String UPDATE_STYLE = "update styles set style = :style " +
                "where style in (select style from styles " +
                "left join books on books.style_id = styles.id " +
                "where books.id = :id)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("book", bookDTO.getName());
        params.addValue("author", bookDTO.getAuthor());
        params.addValue("style", bookDTO.getStyle());
        params.addValue("id", id);
        try {
            namedParameterJdbcTemplate.update(UPDATE_BOOK, params);
            namedParameterJdbcTemplate.update(UPDATE_AUTHOR, params);
            namedParameterJdbcTemplate.update(UPDATE_STYLE, params);
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
            final String name = rs.getString("book");
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
