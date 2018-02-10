package QuiZ.Questions;

public class MediaStringQuestion implements Question{
    Integer id;
    Integer points;
    String questionText;
    String answer;
    String media;


    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getQuestionText() {
        return this.questionText;
    }

    @Override
    public void setPoints(Integer points) {
        this.points = points;
    }

    @Override
    public String[] getAnswers() {
        String[] ret = {answer};
        return ret;
    }

    @Override
    public void setAnswers(String[] answers) {
        this.answer = answers[0];
    }

    @Override
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    @Override
    public Integer getPoints() {
        return this.points;
    }

    @Override
    public void Question(Integer id, String question, String[] answers, Integer rightAnswer) {
        System.out.println("please use the other constructor. Data will be stored.");
    }

    @Override
    public void Question(Integer id, String question, String[] answers) {
        try{
            this.id = id;
            this.questionText = question;
            this.answer = answers[0];

        }catch (ClassCastException e){
            e.printStackTrace();
        }
    }

    public MediaStringQuestion(Integer id, String question, String[] answers, Integer rightAnswer){
        this.Question(id, question, answers, rightAnswer);
    }

    public MediaStringQuestion(Integer id, String question, String[] answers){
        this.Question(id, question, answers);
    }
}
