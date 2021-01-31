package com.books.books.mongock.changelog;

import com.books.books.models.Author;
import com.books.books.models.Book;
import com.books.books.models.Comment;
import com.books.books.models.Style;
import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import lombok.val;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {

    private Book book;
    private Style style;
    private Author author;
    private Comment comment;
    private Set<Author> authors = new HashSet<>();
    private Set<Style> styles = new HashSet<>();

    public void initAuthors(){
        authors.add(new Author(1,"Джек Лондон", null));
        authors.add(new Author(2, "Лев Толстой", null));
        authors.add(new Author(3, "Уильям Шекспир", null));
        authors.add(new Author(4, "Чарльз Дикенс", null));
        authors.add(new Author(5, "Александр Пушкин", null));
    }

    public void initStyles(){
        styles.add(new Style(1,"приключения", null));
        styles.add(new Style(2,"роман", null));
        styles.add(new Style(3, "трагедия", null));
        styles.add(new Style(4,"поэма", null));
    }

    @ChangeSet(order = "001", id = "dropDB", author = "Dyadkin Maxim", runAlways = true)
    public void dropDB(MongoDatabase database){
        database.drop();
    }

    @ChangeSet(order = "002", id = "initAuthors", author = "Dyadkin Maxim", runAlways = true)
    public void saveAuthors(MongoTemplate template, Set<Author> authors){
       for(Author author : authors){
           template.save(author);
       }
    }

    @ChangeSet(order = "003", id = "initStyles", author = "Dyadkin Maxim", runAlways = true)
    public void saveStyles(MongoTemplate template, Set<Style> styles){
        for(Style style : styles){
            template.save(style);
        }
    }

    @ChangeSet(order = "002", id = "initBooks", author = "Dyadkin Maxim", runAlways = true)
    public void initBooks(MongoTemplate template) {
        Book book1 = new Book(1,
                                "Белый клык",
                                new Author(1, "Джек Лондон", null),
                                new Style(1, "приключения", null),
                                null);
        template.save(new Comment(1, "гуд", book1));
        template.save(new Comment(2, "не гуд", book1));
        template.save(book1);
        Book book2 = new Book(2,
                "Белый клык",
                new Author(1, "Джек Лондон", null),
                new Style(1, "приключения", null),
                null);
        template.save(new Comment(1, "гуд", book1));
        template.save(new Comment(2, "не гуд", book1));
        template.save(book1);

    }

//    insert into books values (1, 'Белый клык', 1, 1);
//    insert into books values (2, 'Война и мир', 2, 2);
//    insert into books values (3, 'Гамлет', 3, 3);
//    insert into books values (4, 'Дэвид Копперфильд', 4, 2);
//    insert into books values (5, 'Вольность', 5, 4);

//    @ChangeSet(order = "004", id = "initComments", author = "Dyadkin Maxim", runAlways = true)
//    public void initComments(MongoTemplate template){
//        template.save(new Comment(1, "гуд", book1));
//        template.save(new Comment(2, "не гуд", 1));
//        template.save(new Comment(3, "продам гараж, тел. +7-916-439-58-68", 2));
//        template.save(new Comment(4, "Когда кончится ковид?", 3));
//        template.save(new Comment(5, "ай да Пушкин, ай да...", 5));
//        template.save(new Comment(6, "не читал, но осуждаю", 5));
//        template.save(new Comment(7, "очень понравилось", 3));
//        template.save(new Comment(8, "лажа", 4));
//
//    }

}
