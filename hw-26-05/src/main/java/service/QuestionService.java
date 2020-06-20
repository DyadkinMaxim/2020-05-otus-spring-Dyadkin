package service;

import dao.QuestionDAO;
import dto.QuestionDTO;

import java.util.List;

public class QuestionService {
    private QuestionDAO dao;

    public QuestionService(QuestionDAO dao) {
        this.dao = dao;
    }

    public void printQuestions() {
        List<QuestionDTO> questionDTOS = dao.getRecords();
        for (QuestionDTO questionDTO : questionDTOS) {
            String questionText = questionDTO.getQuestionID() + " " + questionDTO.getQuestionText();
            String answersText = questionDTO.getAnswersText();
            System.out.println(questionText);
            System.out.println("Choose your answer: " + answersText);
        }
    }
}
