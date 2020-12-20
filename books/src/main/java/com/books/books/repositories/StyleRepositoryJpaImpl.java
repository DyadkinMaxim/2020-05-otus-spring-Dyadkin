package com.books.books.repositories;


import com.books.books.models.Author;
import com.books.books.models.Book;
import com.books.books.models.Comment;
import com.books.books.models.Style;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    public List<Book> findBooksByStyle(String styleName) {
        TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
        try {
            List<Book> books = query.getResultList();
            List<Book> booksByStyle = new ArrayList<>();
            for(Book bookByStyle : books){
                if(Objects.equals(bookByStyle.getStyle().getStyleName(), styleName)){
                    booksByStyle.add(bookByStyle);
                }
            }
            return booksByStyle;
        } catch (PersistenceException e) {
            return null;
        }
    }

    @Override
    public List<Author> findAuthorsByStyle(String styleName) {
        TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
        try {
            List<Book> books = query.getResultList();
            List<Author> authorsByStyle = new ArrayList<>();
            for(Book bookByStyle : books){
                if(Objects.equals(bookByStyle.getStyle().getStyleName(), styleName)){
                    authorsByStyle.add(bookByStyle.getAuthor());
                }
            }
            return authorsByStyle;
        } catch (PersistenceException e) {
            return null;
        }
    }

    @Override
    public int deleteById(long id) {
        int resultSuccess = 0;
        try {
            Style styleById = findById(id).orElse(new Style());
            if(!(styleById.getId() == 0)){
                resultSuccess = 1;
            }
            em.remove(styleById);
        } catch (PersistenceException e) {
            System.out.println("Не удалось удалить жанр");
        }
        return resultSuccess;
    }
}
