package QuiZ.Questions;

public class MultipleChoiceQuestion implements Question{
    Integer id;
    Integer points;
    String questionText;
    String[] answers;
    Integer answer;


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
        return answers;
    }

    @Override
    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    @Override
    public void Question(Integer id, String question, String[] answers, Integer rightAnswer) {
        try{
            this.id = id;
            this.questionText = question;
            this.answers = answers;
            this.answer = rightAnswer;

        }catch (ClassCastException e){
            e.printStackTrace();
        }
    }

    @Override
    public void Question(Integer id, String question, String[] answers) {
        System.out.println("please use the other constructor. No data will be stored!");
    }

    @Override
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    @Override
    public Integer getPoints() {
        return this.points;
    }

    public MultipleChoiceQuestion(Integer id, String question, String[] answers, Integer rightAnswer){
        this.Question(id, question, answers, rightAnswer);
    }

    public MultipleChoiceQuestion(Integer id, String question, String[] answers){
        this.Question(id, question, answers);
    }
}
