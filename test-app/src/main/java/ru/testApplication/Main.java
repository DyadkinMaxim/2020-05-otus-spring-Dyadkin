package ru.testApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.testApplication.config.YamlProps;
import ru.testApplication.service.QuestionServiceImpl;

@SpringBootApplication
@EnableConfigurationProperties(YamlProps.class)
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Main.class, args);
        QuestionServiceImpl questionService = context.getBean(QuestionServiceImpl.class);
        questionService.printQuestions();
    }
}
