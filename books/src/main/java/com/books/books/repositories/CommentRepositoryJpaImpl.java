package com.books.books.repositories;

import com.books.books.models.Book;
import com.books.books.models.Comment;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class CommentRepositoryJpaImpl implements CommentRepositoryJpa {

    @PersistenceContext
    EntityManager em;

    @Override
    public Optional<Long> save(Comment comment) {
        try {
            em.persist(comment);
        } catch (PersistenceException e) {
            System.out.println("Не удалось добавить комментарий");
        }
        return Optional.ofNullable(comment.getId());
    }

    @Override
    public List<Comment> findAll() {
        TypedQuery<Comment> query = em.createQuery("select c from Comment c", Comment.class);
        return query.getResultList();
    }

    @Override
    public Optional<Comment> findById(long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    @Override
    public int deleteById(long id) {
        int resultSuccess = 0;
        try {
            Comment commentById = findById(id).orElse(new Comment());
            if(!(commentById.getId() == 0)){
                resultSuccess = 1;
            }
            em.remove(commentById);
        } catch (PersistenceException e) {
            System.out.println("Не удалось удалить комментарий");
        }
        return resultSuccess;
    }
}
