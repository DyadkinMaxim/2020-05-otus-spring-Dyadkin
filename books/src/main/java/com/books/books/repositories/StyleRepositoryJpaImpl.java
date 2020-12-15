package com.books.books.repositories;


import com.books.books.models.Author;
import com.books.books.models.Book;
import com.books.books.models.Comment;
import com.books.books.models.Style;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public class StyleRepositoryJpaImpl implements StyleRepositoryJpa {
    @PersistenceContext
    EntityManager em;

    @Transactional
    @Override
    public Optional<Long> save(Style style) {
        try {
            em.persist(style);
        } catch (PersistenceException e) {
            System.out.println("Не удалось добавить жанр");
        }
        return Optional.ofNullable(style.getId());
    }

    @Transactional
    @Override
    public List<Style> findAll() {
        TypedQuery<Style> query = em.createQuery("select s from Style s", Style.class);
        return query.getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Style> findById(long id) {
        return Optional.ofNullable(em.find(Style.class, id));
    }

    @Transactional
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

    @Transactional
    @Override
    public List<Book> findBooksByStyle(String styleName) {
        TypedQuery<Book> query = em.createQuery("select b from Book b where b.style = :style", Book.class);
        try {
            Style styleByName = findByName(styleName);
            query.setParameter("style", styleByName);
            return query.getResultList();
        } catch (PersistenceException e) {
            return null;
        }
    }

    @Transactional
    @Override
    public List<Author> findAuthorsByStyle(String styleName) {
        TypedQuery<Author> query = em.createQuery("select b.author from Book b " +
                "where b.style = :style", Author.class);
        try {
            Style styleByName = findByName(styleName);
            query.setParameter("style", styleByName);
            return query.getResultList();
        } catch (PersistenceException e) {
            return null;
        }
    }

    @Transactional
    @Override
    public int deleteById(long id) {
        Style styleById = findById(id).orElse(new Style());
        int resultSuccess = 0;
        try {
            em.remove(styleById);
            em.flush();
            em.clear();
            resultSuccess = 1;
        } catch (PersistenceException e) {
            System.out.println("Не удалось удалить жанр");
        }
        return resultSuccess;
    }
}
