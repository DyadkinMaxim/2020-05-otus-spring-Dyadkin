package com.books.books.repositories;

import com.books.books.models.Book;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Repository
public class BookRepositoryJpaImpl implements BookRepositoryJpa {

    @PersistenceContext
    EntityManager em;

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
        TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
        return query.getResultList();
    }



    @Override
    public Optional<Book> findById(long id) {
        EntityGraph<?> commentGraph = em.getEntityGraph("comment-entity-graph");
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.fetchgraph", commentGraph);

        return Optional.ofNullable(em.find(Book.class, id));
    }


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

    @Override
    public int deleteById(long id) {

        int resultSuccess = 0;
        try {
            Book bookById = findById(id).orElse(new Book());
            if(!(bookById.getId() == 0)){
                resultSuccess = 1;
            }
            em.remove(bookById);
        } catch (PersistenceException e) {
            System.out.println("Не удалось удалить книгу");
        }
        return resultSuccess;
    }
}
