package com.books.books.dao;

import com.books.books.dto.AuthorDTO;
import com.books.books.dto.BookDTO;
import com.books.books.dto.StyleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class AuthorDAOImpl implements AuthorDAO{

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<AuthorDTO> getAuthors(){
        final String SELECT_AUTHORS = "SELECT id, author FROM AUTHORS";
        return namedParameterJdbcTemplate.query(SELECT_AUTHORS, new AuthorMapper());
    }

    @Override
    public List<BookDTO> getAuthorBooks(String name) {
        final String SELECT_AUTHOR_BOOKS = "SELECT books.id, books.book, authors.author, styles.STYLE FROM authors " +
        "left join books on books.author_id = authors.id " +
        "left join styles on styles.id = books.style_id " +
        "where author = :name";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", name);
        return  namedParameterJdbcTemplate.query(SELECT_AUTHOR_BOOKS, params, new BookDAOImpl.BookMapper());
    }

    @Override
    public List<StyleDTO> getAuthorStyles(String name) {
        final String SELECT_AUTHOR_STYLES = "SELECT styles.id, style FROM STYLES " +
                " WHERE STYLE IN (SELECT DISTINCT STYLE FROM styles " +
                "left join books on styles.id = books.style_id " +
                "left join authors on authors.id = books.author_id " +
                "WHERE AUTHOR = :name)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", name);
        return  namedParameterJdbcTemplate.query(SELECT_AUTHOR_STYLES, params, new StyleDAOImpl.StyleMapper());
    }


    public static class AuthorMapper implements RowMapper<AuthorDTO> {
        @Override
        public AuthorDTO mapRow(ResultSet rs, int i) throws SQLException {
            final long id = rs.getLong("id");
            final String name = rs.getString("author");
            return new AuthorDTO(
                    id,
                    name
                    );
        }
    }
}
