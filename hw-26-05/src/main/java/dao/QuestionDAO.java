package dao;


import dto.QuestionDTO;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuestionDAO {

    /**
     * путь к CSV-файлу с вопросами и ответами
     */
    private final String pathCsvFile;


    public QuestionDAO(String pathCsvFile) {
        this.pathCsvFile = pathCsvFile;

    }

    public List<QuestionDTO> getRecords() {
        ArrayList<QuestionDTO> questionDTOs = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(
                    pathCsvFile));
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
        }
        return questionDTOs;
    }
}
