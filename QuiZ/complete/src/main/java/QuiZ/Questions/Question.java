package QuiZ.Questions;

public interface Question {
    Integer id = 0;
    String questionText  = "";
    String[] answers = {};
    Integer points = 0;

    Integer getId();
    void setId(Integer id);
    String getQuestionText();
    void setQuestionText(String questionText);
    Integer getPoints();
    void setPoints(Integer points);
    String[] getAnswers();
    void setAnswers(String[] answers);
    void Question(Integer id, String question, String[] answers);
    void Question(Integer id, String question, String[] answers, Integer rightAnswer);
}