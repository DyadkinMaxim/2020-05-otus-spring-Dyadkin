package com.books.books.mongock.changelog;

import com.github.cloudyrock.mongock.SpringMongock;
import com.github.cloudyrock.mongock.SpringMongockBuilder;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongockConfig {

        @Bean
        public SpringMongock mongock(MongoClient mongoClient) {
            return new SpringMongockBuilder(mongoClient, "mongoBooks", "com.books.books.mongock.changelog")
                    .build();
        }
    }
