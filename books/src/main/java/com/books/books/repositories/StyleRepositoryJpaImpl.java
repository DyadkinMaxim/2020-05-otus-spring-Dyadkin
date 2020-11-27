package com.books.books.repositories;


import com.books.books.models.Author;
import com.books.books.models.Book;
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

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
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
    public int update(Style style) {
        Query query = em.createQuery("update Style s set s.styleName = :styleName where s.id = :id");
        query.setParameter("styleName", style.getStyleName());
        query.setParameter("id", style.getId());
        int resultSuccess = 0;
        try {
            resultSuccess = query.executeUpdate();
        } catch (PersistenceException e) {
            System.out.println("Не удалось изменить жанр");
        }
        return resultSuccess;
    }

    @Transactional
    @Override
    public int deleteById(long id) {
        Query query = em.createQuery("delete from Style s where s.id = :id");
        query.setParameter("id", id);
        int resultSuccess = 0;
        try {
            resultSuccess = query.executeUpdate();
        } catch (PersistenceException e) {
            System.out.println("Не удалось удалить жанр");
        }
        return resultSuccess;
    }
}
