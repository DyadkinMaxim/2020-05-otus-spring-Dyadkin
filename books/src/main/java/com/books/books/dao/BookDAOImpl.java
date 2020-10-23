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
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class BookDAOImpl implements BookDAO {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private DataSource dataSource;


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


//    @Override
//    public long addBook(BookDTO bookDTO) {
//        //final String SELECT_MAX_ID = "SELECT MAX(ID) FROM BOOKS";
//        //final long id = jdbc.queryForObject(SELECT_MAX_ID, Long.class) +1L;
//        final String ADD_NEW_BOOK = "INSERT INTO BOOKS ( name, author, style) VALUES (" +
//                ":name, " +
//                ":author, " +
//                ":style)";
//        MapSqlParameterSource params = new MapSqlParameterSource();
//        params.addValue("name", bookDTO.getName());
//        params.addValue("author", bookDTO.getAuthor());
//        params.addValue("style", bookDTO.getStyle());
//        namedParameterJdbcTemplate.update(ADD_NEW_BOOK, params);
//
//    }
//
//    public long insert(String message) {
//        Map<String, Object> parameters = new HashMap<>(1);
//        parameters.put("message", message);
//        Number newId = simpleJdbcInsert.executeAndReturnKey(parameters);
//        return (long) newId;
//    }

    @Override
    public void updateBook(BookDTO bookDTO, long id) {
        final String UPDATE_BOOK = "UPDATE BOOKS SET " +
                "BOOKS.book = :book " +
                "WHERE books.id = :id";
        final String UPDATE_AUTHOR = "UPDATE " +
                "(SELECT " +
                "AUTHORS.author, books.id, books.author_id " +
                "from authors " +
                "left join books on books.author_id = authors.id) " +
                "SET " +
                "AUTHORS.author = :author " +
                "WHERE BOOKS.author_id = authors.id " +
                "and books.id = :id";
        final String UPDATE_STYLE = "UPDATE " +
                "(SELECT " +
                "STYLES.style, books.id, books.style_id " +
                "from styles " +
                "left join books on books.style_id = styles.id) " +
                "SET " +
                "STYLES.style = :style " +
                "WHERE BOOKS.style_id = styles.id " +
                "and books.id = :id";
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
