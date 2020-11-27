package com.books.books.repositories;

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

    @Transactional
    @Override
    public Optional<Long> save(Comment comment) {
        try {
            em.persist(comment);
        } catch (PersistenceException e) {
            System.out.println("Не удалось добавить комментарий");
        }
        return Optional.ofNullable(comment.getId());
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> findAll() {
        TypedQuery<Comment> query = em.createQuery("select c from Comment c", Comment.class);
        return query.getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Comment> findById(long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    @Transactional
    @Override
    public int updateCommentText(Comment comment) {
        Query query = em.createQuery("update Comment c set " +
                "c.commentText = :commentText " +
                " where c.id = :id");
        query.setParameter("commentText", comment.getCommentText());
        query.setParameter("id", comment.getId());
        int resultSuccess = 0;
        try {
            resultSuccess = query.executeUpdate();
        } catch (PersistenceException e) {
            System.out.println("Не удалось изменить комментарий");
        }
        return resultSuccess;
    }

    @Transactional
    @Override
    public int deleteById(long id) {
        Query query = em.createQuery("delete from Comment c where c.id = :id");
        query.setParameter("id", id);
        int resultSuccess = 0;
        try {
            resultSuccess = query.executeUpdate();
        } catch (PersistenceException e) {
            System.out.println("Не удалось удалить комментарий");
        }
        return resultSuccess;
    }
}
