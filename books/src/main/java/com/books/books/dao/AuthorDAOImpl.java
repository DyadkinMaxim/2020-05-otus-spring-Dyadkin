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
        final String SELECT_AUTHORS = "SELECT * FROM AUTHORS";
        return namedParameterJdbcTemplate.query(SELECT_AUTHORS, new AuthorMapper());
    }

    @Override
    public List<BookDTO> getAuthorBooks(String name) {
        final String SELECT_AUTHOR_BOOKS = "SELECT * FROM BOOKS WHERE AUTHOR = :name";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", name);
        return  namedParameterJdbcTemplate.query(SELECT_AUTHOR_BOOKS, params, new BookDAOImpl.BookMapper());
    }

    @Override
    public List<StyleDTO> getAuthorStyles(String name) {
        final String SELECT_AUTHOR_STYLES = "SELECT * FROM STYLES " +
                " WHERE STYLE IN (SELECT DISTINCT STYLE FROM BOOKS WHERE AUTHOR = :name)";
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
