package com.books.books.repositories;


import com.books.books.models.Author;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorRepositoryJpaImpl implements AuthorRepositoryJpa {
    @PersistenceContext
    EntityManager em;

    @Override
    public Optional<Long> save(Author author) {
        try {
            em.persist(author);
        } catch (PersistenceException e) {
            System.out.println("Не удалось добавить автора");
        }
        return Optional.ofNullable(author.getId());
    }

    @Override
    public List<Author> findAll() {
        TypedQuery<Author> query = em.createQuery("select a from Author a", Author.class);
        return query.getResultList();
    }


    @Override
    public Optional<Author> findById(long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }


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

    @Override
    public void deleteById(long id) {
        try {
            Author authorById = findById(id).orElse(new Author());
            em.remove(authorById);
            System.out.println("Удален автор с id:" + id);
        } catch (PersistenceException e) {
            System.out.println("Не удалось удалить автора");
        }
    }
}
