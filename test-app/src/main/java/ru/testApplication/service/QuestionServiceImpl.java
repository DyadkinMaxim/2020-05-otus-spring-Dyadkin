package ru.testApplication.service;


import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.testApplication.config.YamlProps;
import ru.testApplication.dto.QuestionDTO;
import ru.testApplication.dao.QuestionDAOImpl;

import java.util.List;
import java.util.Scanner;


@Service
public class QuestionServiceImpl implements QuestionService{

    private final QuestionDAOImpl dao;

    private YamlProps props;
    private final MessageSource messageSource;

    public QuestionServiceImpl(QuestionDAOImpl dao, YamlProps props, MessageSource messageSource) {
        this.dao = dao;
        this.props = props;
        this.messageSource = messageSource;
    }

    public void printQuestions() {
        final int correctAnswersAmount = props.getLimit();
        List<QuestionDTO> questionDTOS = dao.getRecords();
        Scanner scanner = new Scanner(System.in);
        int  correcctCounter = 0;
        String resultTest;
        for (QuestionDTO questionDTO : questionDTOS) {
            String questionText = questionDTO.getQuestionID() + " " + questionDTO.getQuestionText();
            String answersText = questionDTO.getAnswersText();
            System.out.println(questionText);
            String message = messageSource.getMessage("user.answer", new String[]{}, props.getLocale());
            System.out.println(message + answersText);
            if (scanner.hasNextLine()) {
                String userAnswer = scanner.nextLine();
                if (questionDTO.getCorrectAnswerText().equals(userAnswer)) {
                    correcctCounter++;
                }
            }
        }
        if(correcctCounter >= correctAnswersAmount){
            resultTest = messageSource.getMessage("pass.result",  new String[]{}, props.getLocale());
        }
        else {resultTest = messageSource.getMessage("fall.result",  new String[]{String.valueOf(props.getLimit())}, props.getLocale()); }
        System.out.println(messageSource.getMessage("user.result",   new String[]{String.valueOf(correcctCounter)},
                props.getLocale()) + "\n" + resultTest);
    }
}
