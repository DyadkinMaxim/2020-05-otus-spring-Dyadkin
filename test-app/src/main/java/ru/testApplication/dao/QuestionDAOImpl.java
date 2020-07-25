package ru.testApplication.dao;


import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import ru.testApplication.config.YamlProps;
import ru.testApplication.dto.QuestionDTO;


import java.io.BufferedReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


@Service
public class QuestionDAOImpl implements QuestionDAO {

    /**
     * конфиги
     */
    private final YamlProps props;

    private final MessageSource messageSource;

    public QuestionDAOImpl(YamlProps props, MessageSource messageSource) {
        this.props = props;
        this.messageSource = messageSource;

    }

    public List<QuestionDTO> getRecords() {
        ArrayList<QuestionDTO> questionDTOs = new ArrayList<>();

        try {
            ClassLoader classLoader = getClass().getClassLoader();
            final String pathCsvFile = messageSource.getMessage("question.path", new String[]{}, props.getLocale());
            File file = new File(classLoader.getResource(pathCsvFile).getFile());
            BufferedReader reader = new BufferedReader(new FileReader(file
            ));
            // считываем построчно
            String line;
            Scanner scanner;
            int index = 0;

            while ((line = reader.readLine()) != null) {
                scanner = new Scanner(line);
                scanner.useDelimiter(",");
                QuestionDTO questionDTO = new QuestionDTO();
                while (scanner.hasNext()) {
                    String data = scanner.next();
                    if (index == 0) {
                        questionDTO.setQuestionID(Integer.parseInt(data));
                    } else if (index == 1) {
                        questionDTO.setQuestionText(data);
                    } else if (index == 2) {
                        questionDTO.setAnswersText(data);
                    } else if (index == 3) {
                        questionDTO.setCorrectAnswerText(data);
                    } else {
                        System.out.println("Некорректные данные::" + data);
                    }
                    index++;
                }
                questionDTOs.add(questionDTO);
                index = 0;

            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return questionDTOs;
    }
}
