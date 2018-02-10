package QuiZ.Questions;

public class MediaGuessQuestion implements Question{
    Integer id;
    Integer points;
    String questionText;
    Integer answer;
    String media;


    public Integer getAnswer() {
        return answer;
    }

    public void setAnswer(Integer answer) {
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
        String[] ret = {answer.toString()};
        return ret;
    }

    @Override
    public void setAnswers(String[] answers) {
        answer = Integer.parseInt(answers[0]);
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
            this.answer = Integer.parseInt(answers[0]);

        }catch (ClassCastException e){
            e.printStackTrace();
        }
    }

    public MediaGuessQuestion(Integer id, String question, String[] answers, Integer rightAnswer){
        this.Question(id, question, answers, rightAnswer);
    }

    public MediaGuessQuestion(Integer id, String question, String[] answers){
        this.Question(id, question, answers);
    }
}
