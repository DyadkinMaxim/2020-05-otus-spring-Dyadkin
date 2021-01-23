package com.books.books.repositories;

import com.books.books.models.Style;
import com.books.books.repositoriesSpringDataJPA.StyleRepository;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Spring Data Jpa для работы с жанрами ")
@DataJpaTest
public class StyleRepositoryTest {

    private static final String FIRST_STYLE_NAME = "приключения";
    private static final long FIRST_STYLE_ID = 1;

    @Autowired
    private StyleRepository styleRepository;

    @Autowired
    private TestEntityManager em;

    @DisplayName(" должен корректно искать жанр по имени")
    @Test
    void shouldFindExpectedStyleByName() {
        val firstStyle = em.find(Style.class, FIRST_STYLE_ID);
        Style actualStyle = styleRepository.findByStyleNameContains(FIRST_STYLE_NAME);
        assertThat(actualStyle).isEqualToComparingFieldByField(firstStyle);
    }
}
