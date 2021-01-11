package com.books.books.repositories;


import com.books.books.models.Style;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class StyleRepositoryJpaImpl implements StyleRepositoryJpa {
    @PersistenceContext
    EntityManager em;

    @Override
    public Optional<Long> save(Style style) {
        try {
            em.persist(style);
        } catch (PersistenceException e) {
            System.out.println("Не удалось добавить жанр");
        }
        return Optional.ofNullable(style.getId());
    }

    @Override
    public List<Style> findAll() {
        TypedQuery<Style> query = em.createQuery("select s from Style s", Style.class);
        return query.getResultList();
    }

    @Override
    public Optional<Style> findById(long id) {
        return Optional.ofNullable(em.find(Style.class, id));
    }

    @Override
    public Style findByName(String name) {
        TypedQuery<Style> query = em.createQuery("select s from Style s where s.styleName = :name", Style.class);
        query.setParameter("name", name);
        try {
            return query.getSingleResult();
        } catch (PersistenceException e) {
            return null;
        }
    }

    @Override
    public void deleteById(long id) {
        try {
            Style styleById = findById(id).orElse(new Style());
            em.remove(styleById);
            System.out.println("Удален жанр с id:" + id);
        } catch (PersistenceException e) {
            System.out.println("Не удалось удалить жанр");
        }
    }
}
