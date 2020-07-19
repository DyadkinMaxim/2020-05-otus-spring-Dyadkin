package ru.testApplication.dao;

import ru.testApplication.dto.QuestionDTO;

import java.util.List;

public interface QuestionDAO {

    List<QuestionDTO> getRecords();

}
