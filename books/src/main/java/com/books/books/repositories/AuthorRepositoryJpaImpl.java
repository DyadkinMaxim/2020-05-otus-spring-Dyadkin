package com.books.books.repositories;


import com.books.books.models.Author;
import com.books.books.models.Book;
import com.books.books.models.Style;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    public List<Book> findBooksByAuthor(String authorName) {
        TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
        try {
          List<Book> books = query.getResultList();
          List<Book> booksByAuthor = new ArrayList<>();
          for(Book bookByAuthor : books){
              if(Objects.equals(bookByAuthor.getAuthor().getAuthorName(), authorName)){
                  booksByAuthor.add(bookByAuthor);
              }
          }
            return booksByAuthor;
        } catch (PersistenceException e) {
            return null;
        }
    }

    @Override
    public List<Style> findStylesByAuthor(String authorName) {
        TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
        try {
            List<Book> books = query.getResultList();
            List<Style> stylesByAuthor = new ArrayList<>();
            for(Book bookByAuthor : books){
                if(Objects.equals(bookByAuthor.getAuthor().getAuthorName(), authorName)){
                    stylesByAuthor.add(bookByAuthor.getStyle());
                }
            }
            return stylesByAuthor;
        } catch (PersistenceException e) {
            return null;
        }
    }


    @Override
    public int deleteById(long id) {
        int resultSuccess = 0;
        try {
            Author authorById = findById(id).orElse(new Author());
            if(!(authorById.getId() == 0)){
                resultSuccess = 1;
            }
            em.remove(authorById);
        } catch (PersistenceException e) {
            System.out.println("Не удалось удалить автора");
        }
        return resultSuccess;
    }
}
