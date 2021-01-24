package com.books.books;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
@SpringBootApplication
public class BooksApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(BooksApplication.class, args);
	}
}