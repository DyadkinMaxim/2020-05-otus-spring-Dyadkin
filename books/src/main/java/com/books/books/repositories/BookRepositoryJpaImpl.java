package com.books.books.repositories;

import com.books.books.models.Book;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;


@Repository
public class BookRepositoryJpaImpl implements BookRepositoryJpa {

    @PersistenceContext
    EntityManager em;

    @Transactional
    @Override
    public Optional<Long> save(Book book) {
        try {
            em.persist(book);
        } catch (PersistenceException e) {
            System.out.println("Не удалось добавить книгу");
        }
        return Optional.ofNullable(book.getId());
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> findAll() {
        EntityGraph<?> authorEntityGraph = em.getEntityGraph("author-entity-graph");
        EntityGraph<?> styleEntityGraph = em.getEntityGraph("style-entity-graph");
        EntityGraph<?> commentEntityGraph = em.getEntityGraph("comment-entity-graph");
        TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
        query.setHint("javax.persistence.fetchgraph", authorEntityGraph);
        query.setHint("javax.persistence.fetchgraph", styleEntityGraph);
        query.setHint("javax.persistence.fetchgraph", commentEntityGraph);
        return query.getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Book> findById(long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Transactional(readOnly = true)
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
    public int updateBookName(Book book) {
        Query query = em.createQuery("update Book b set" +
                " b.bookName = :bookName" +
                " where b.id = :id");
        query.setParameter("bookName", book.getBookName());
        query.setParameter("id", book.getId());
        int resultSuccess = 0;
        try {
            resultSuccess = query.executeUpdate();
        } catch (PersistenceException e) {
            System.out.println("Не удалось изменить книгу");
        }
        return resultSuccess;
    }

    @Transactional
    @Override
    public int deleteById(long id) {
        Query query = em.createQuery("delete from Book b where b.id = :id");
        query.setParameter("id", id);
        int resultSuccess = 0;
        try {
            resultSuccess = query.executeUpdate();
        } catch (PersistenceException e) {
            System.out.println("Не удалось удалить книгу");
        }
        return resultSuccess;
    }
}
