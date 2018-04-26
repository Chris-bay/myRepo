package QuiZ.Questions;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Enumerated
    QuestionType type;

    Integer points;
    String questionText;
    String[] answers = {"", "", "", ""};
    Integer answer;
    String media;
    Integer qorder;

    public Question(){};

    public Integer getAnswer() {
        return answer;
    }

    public void setAnswer(Integer answer) {
        this.answer = answer;
    }

    public Integer getId() {
        return id;
    }

    public String getQuestionText() {
        return this.questionText;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String[] getAnswers() {
        return answers;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    public Question(QuestionType type,String question, String[] answers, Integer rightAnswer, String media) {
        this.questionText = question;
        this.answers = answers;
        this.answer = rightAnswer;
        this.type = type;
        this.media = media;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public Integer getPoints() {
        return this.points;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getMedia() {
        return media;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public QuestionType getType() {
        return type;
    }

    public Integer getOrder() {
        return qorder;
    }

    public void setOrder(Integer qorder) {
        this.qorder = qorder;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}