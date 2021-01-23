package com.books.books.repositories;

import com.books.books.models.Author;
import com.books.books.repositoriesSpringDataJPA.AuthorRepository;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Spring Data Jpa для работы с авторами ")
@DataJpaTest
public class AuthorRepositoryTest {

    private static final String FIRST_AUTHOR_NAME = "Джек Лондон";
    private static final long FIRST_AUTHOR_ID = 1;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private TestEntityManager em;

    @DisplayName(" должен корректно искать автора по имени")
    @Test
    void shouldFindExpectedAuthorByName() {
        val firstAuthor = em.find(Author.class, FIRST_AUTHOR_ID);
        Author actualAuthor = authorRepository.findByAuthorNameContains(FIRST_AUTHOR_NAME);
        assertThat(actualAuthor).isEqualToComparingFieldByField(firstAuthor);
    }
}
