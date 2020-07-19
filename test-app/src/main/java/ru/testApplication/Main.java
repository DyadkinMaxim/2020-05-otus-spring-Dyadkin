package ru.testApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.testApplication.config.YamlProps;
import ru.testApplication.service.QuestionServiceImpl;

@SpringBootApplication
@EnableConfigurationProperties(YamlProps.class)
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(Main.class);
        QuestionServiceImpl questionService = context.getBean(QuestionServiceImpl.class);
        questionService.printQuestions();

    }
}
