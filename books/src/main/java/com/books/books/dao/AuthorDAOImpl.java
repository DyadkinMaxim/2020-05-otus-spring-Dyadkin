package com.books.books.dao;

import com.books.books.dto.AuthorDTO;
import com.books.books.dto.BookDTO;
import com.books.books.dto.StyleDTO;
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
public class AuthorDAOImpl implements AuthorDAO{


    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private JdbcTemplate jdbcTemplate;

    public AuthorDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long addAuthor(String name){
        final SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);

        insert.setGeneratedKeyName("id");

        final Map<String, Object> data = new HashMap<>();
        data.put("author", name);

        final List<String> columns = new ArrayList<>();
        columns.add("author");

        insert.setTableName("authors");
        insert.setColumnNames(columns);

        final Long id = (Long) insert.executeAndReturnKey(data);
        return id;
    }

    @Override
    public Long getAuthorIdByName(String name){
        final String SELECT_AUTHOR_BY_NAME = "select id from authors where author = :name";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", name);
        try {
            return namedParameterJdbcTemplate.queryForObject(SELECT_AUTHOR_BY_NAME, params, Long.class);
        }
        catch (DataAccessException e) {
            return null;
        }
    }

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
