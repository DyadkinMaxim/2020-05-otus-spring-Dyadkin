package testApp.dto;

public class QuestionDTO {
    /**
     * ID вопроса
     */
    private int questionID;
    /**
     * текст вопроса
     */
    private String questionText;
    /**
     * текст ответов
     */
    private String answersText;
    /**
     * текст правильного ответа
     */
    private String correctAnswerText;

    public int getQuestionID() {
        return questionID;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getAnswersText() {
        return answersText;
    }

    public String getCorrectAnswerText() {
        return correctAnswerText;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public void setAnswersText(String answersText) {
        this.answersText = answersText;
    }

    public void setCorrectAnswerText(String correctAnswerText) {
        this.correctAnswerText = correctAnswerText;
    }

    @Override
    public String toString() {
        return "\nID=" + getQuestionID() + "::Текст вопроса" + getQuestionText() + "::Текст ответов" + getAnswersText() + "::Текст правильного ответа=" + getCorrectAnswerText();
    }
}
