package ru.testApplication.presentation;

import org.springframework.stereotype.Service;
import ru.testApplication.service.QuestionServiceImpl;

@Service
public class TestFacadeBean implements TestFacade {

    private final QuestionServiceImpl questionService;

    public TestFacadeBean(QuestionServiceImpl questionService){
        this.questionService = questionService;
    }

    @Override
    public void startTest() {
        questionService.printQuestions();
        questionService.checkAnswers();
    }
}
