package QuiZ;

public interface Question {
    Integer id = 0;
    String question  = "";
    Integer points = 0;

    Integer getId();
    void setId(Integer id);
    String getQuestion();
    void setQuestion(String question);
    Integer getPoints();
    void setPoints(Integer points);
}