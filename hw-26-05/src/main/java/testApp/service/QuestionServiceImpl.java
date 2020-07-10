package testApp.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import testApp.dto.QuestionDTO;
import testApp.dao.QuestionDAOImpl;

import java.util.List;
import java.util.Scanner;


@PropertySource("classpath:config.properties")
@Service
public class QuestionServiceImpl implements QuestionService{

    private final QuestionDAOImpl dao;

    private int correctAnswersAmount;

    public QuestionServiceImpl(QuestionDAOImpl dao, @Value("${pass.amount}") int correctAnswersAmount) {
        this.dao = dao;
        this.correctAnswersAmount = correctAnswersAmount;
    }

    public void printQuestions() {
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
