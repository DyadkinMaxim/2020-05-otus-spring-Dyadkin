package ru.testApplication.service;


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

    public QuestionServiceImpl(QuestionDAOImpl dao, YamlProps props) {
        this.dao = dao;
        this.props = props;
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
            System.out.println("Choose your answer: " + answersText);
            if (scanner.hasNextLine()) {
                String userAnswer = scanner.nextLine();
                if (questionDTO.getCorrectAnswerText().equals(userAnswer)) {
                    correcctCounter++;
                }
            }
        }
        if(correcctCounter >= correctAnswersAmount){
            resultTest = "Congrats, you passed this test";
        }
        else {resultTest = "Sorry, you have not passed this test"; }
        System.out.println("Your result is " + correcctCounter + "/5 correct answers" + "\n" + resultTest);
    }
}
