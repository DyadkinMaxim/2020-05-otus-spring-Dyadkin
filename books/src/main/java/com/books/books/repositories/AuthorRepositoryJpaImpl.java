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
public class AuthorRepositoryJpaImpl implements AuthorRepositoryJpa {
    @PersistenceContext
    EntityManager em;

    @Transactional
    @Override
    public Optional<Long> save(Author author) {
        try {
            em.persist(author);
        } catch (PersistenceException e) {
            System.out.println("Не удалось добавить автора");
        }
        return Optional.ofNullable(author.getId());
    }

    @Transactional
    @Override
    public List<Author> findAll() {
        TypedQuery<Author> query = em.createQuery("select a from Author a", Author.class);
        return query.getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Author> findById(long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }

    @Transactional
    @Override
    public Author findByName(String name) {
        TypedQuery<Author> query = em.createQuery("select a from Author a where a.authorName = :name", Author.class);
        query.setParameter("name", name);
        try {
            return query.getSingleResult();
        } catch (PersistenceException e) {
            return null;
        }
    }

    @Transactional
    @Override
    public List<Book> findBooksByAuthor(String authorName) {
        TypedQuery<Book> query = em.createQuery("select b from Book b where b.author = :author", Book.class);
        try {
            Author authorByName = findByName(authorName);
            query.setParameter("author", authorByName);
            return query.getResultList();
        } catch (PersistenceException e) {
            return null;
        }
    }

    @Transactional
    @Override
    public List<Style> findStylesByAuthor(String authorName) {
        TypedQuery<Style> query = em.createQuery("select b.style from Book b " +
                "where b.author = :author", Style.class);
        try {
            Author authorByName = findByName(authorName);
            query.setParameter("author", authorByName);
            return query.getResultList();
        } catch (PersistenceException e) {
            return null;
        }
    }

    @Transactional
    @Override
    public int deleteById(long id) {
        int resultSuccess = 0;
        try {
            Author authorById = findById(id).orElse(new Author());
            em.remove(authorById);
            em.flush();
            em.clear();
            resultSuccess = 1;
        } catch (PersistenceException e) {
            System.out.println("Не удалось удалить автора");
        }
        return resultSuccess;
    }
}
