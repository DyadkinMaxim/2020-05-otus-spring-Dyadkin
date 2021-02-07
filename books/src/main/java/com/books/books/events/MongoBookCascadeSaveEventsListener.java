package com.books.books.events;


import com.books.books.models.Book;
import com.books.books.mongoRepos.AuthorRepository;
import com.books.books.mongoRepos.CommentRepository;
import com.books.books.mongoRepos.StyleRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class MongoBookCascadeSaveEventsListener extends AbstractMongoEventListener<Book> {

    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Book> event) {
        Book book = event.getSource();
        if ((book instanceof Book) && ((Book) book).getAuthor() != null) {
            mongoOperations.save(((Book) book).getAuthor());
        }
        if ((book instanceof Book) && ((Book) book).getStyle() != null) {
            mongoOperations.save(((Book) book).getStyle());
        }
        if ((book instanceof Book) && ((Book) book).getComment() != null) {
            mongoOperations.save(((Book) book).getComment());
        }
    }
}
