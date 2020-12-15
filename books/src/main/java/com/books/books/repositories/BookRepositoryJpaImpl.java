package com.books.books.repositories;

import com.books.books.models.Book;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Repository
public class BookRepositoryJpaImpl implements BookRepositoryJpa {

    @PersistenceContext
    EntityManager em;

    @Transactional
    @Override
    public Optional<Book> save(Book book) {
        if (book.getId() == 0) {
            try {
                em.persist(book);

            } catch (PersistenceException e) {
                System.out.println("Не удалось добавить книгу");
            }
            return Optional.ofNullable(book);
        } else {
            Book mergeBook = new Book();
            try {
                mergeBook = em.merge(book);

            } catch (PersistenceException e) {
                System.out.println("Не удалось добавить книгу");
            }
            return Optional.ofNullable(mergeBook);
        }
    }

    @Override
    public List<Book> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Book> bookCriteriaQuery = cb.createQuery(Book.class);
        Root<Book> rootEntry = bookCriteriaQuery.from(Book.class);
        CriteriaQuery<Book> all = bookCriteriaQuery.select(rootEntry);
        TypedQuery<Book> allQuery = em.createQuery(all);
        return allQuery.getResultList();
    }


    @Transactional(readOnly = true)
    @Override
    public Optional<Book> findById(long id) {
        EntityGraph<?> commentGraph = em.getEntityGraph("comment-entity-graph");
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.fetchgraph", commentGraph);

        return Optional.ofNullable(em.find(Book.class, id, properties));
    }

    @Transactional
    @Override
    public Book findByName(String name) {
        TypedQuery<Book> query = em.createQuery("select b from Book b where b.bookName = :name", Book.class);
        query.setParameter("name", name);
        try {
            return query.getSingleResult();
        } catch (PersistenceException e) {
            return null;
        }
    }

    @Transactional
    @Override
    public int deleteById(long id) {
        Book bookById = findById(id).orElse(new Book());
        int resultSuccess = 0;
        try {
            em.remove(bookById);
            em.flush();
            em.clear();
            resultSuccess = 1;
        } catch (PersistenceException e) {
            System.out.println("Не удалось удалить книгу");
        }
        return resultSuccess;
    }
}
