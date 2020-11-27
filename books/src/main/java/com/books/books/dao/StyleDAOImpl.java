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
public class StyleDAOImpl implements StyleDAO{


    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private JdbcTemplate jdbcTemplate;

    public StyleDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long addStyle(String name) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);

        insert.setGeneratedKeyName("id");

        final Map<String, Object> data = new HashMap<>();
        data.put("style", name);

        final List<String> columns = new ArrayList();
        columns.add("style");

        insert.setColumnNames(columns);
        insert.setTableName("styles");
        final Long id = (Long) insert.executeAndReturnKey(data);
        return id;
    }

    @Override
    public Long getStyleByName(String name) {
        final String SELECT_STYLE_BY_ID = "select id from styles where style  = :name";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", name);
        try {
            return namedParameterJdbcTemplate.queryForObject(SELECT_STYLE_BY_ID, params, Long.class);
        }
        catch(DataAccessException e){
            return null;
        }

    }

    @Override
    public List<StyleDTO> getStyles(){
        final String SELECT_STYLES = "SELECT id, style FROM styles";
        return namedParameterJdbcTemplate.query(SELECT_STYLES, new StyleMapper());
    }

    @Override
    public List<BookDTO> getBooksByStyle(String name){
        final String SELECT_BOOKS_BY_STYLE = "SELECT styles.id, books.book, authors.author, styles.style FROM styles " +
                "left join books on books.style_id = styles.id " +
                "left join authors on authors.id = books.author_id " +
                "WHERE style = :name";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", name);
        return  namedParameterJdbcTemplate.query(SELECT_BOOKS_BY_STYLE, params, new BookDAOImpl.BookMapper());
    }

    @Override
    public List<AuthorDTO> getAuthorsByStyle(String style){
        final String SELECT_AUTHORS_BY_STYLE = "SELECT authors.id, author FROM authors " +
                "left join books on books.author_id = authors.id " +
                "left join styles on books.style_id = styles.id " +
                "WHERE style = :style";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("style", style);
        return  namedParameterJdbcTemplate.query(SELECT_AUTHORS_BY_STYLE, params, new AuthorDAOImpl.AuthorMapper());
    }


    public static class StyleMapper implements RowMapper<StyleDTO> {
        @Override
        public StyleDTO mapRow(ResultSet rs, int i) throws SQLException {
            final long id = rs.getLong("id");
            final String name = rs.getString("style");
            return new StyleDTO(
                    id,
                    name
            );
        }
    }
}
