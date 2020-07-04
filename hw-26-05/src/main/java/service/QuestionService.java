package service;

import dao.QuestionDAO;
import dto.QuestionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    private QuestionDAO dao;

    public QuestionService(@Qualifier("questionDAO") QuestionDAO dao) {
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
