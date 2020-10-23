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
public class StyleDAOImpl implements StyleDAO{

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

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
