package QuiZ.Participate;

public class ParticipateForm {

    private String name;
    private String hashId;
    private String quizId;
    private String questionId;
    private String liveQuizId;
    private String errorMessage;

    private Integer question;
    private Integer answer;

    private Integer retAnswer;
    private String retAnswerStr;
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Integer getQuestion() {
        return question;
    }

    public void setQuestion(Integer question) {
        this.question = question;
    }

    public Integer getAnswer() {
        return answer;
    }

    public void setAnswer(Integer answer) {
        this.answer = answer;
    }

    public Integer getRetAnswer() {
        return retAnswer;
    }

    public void setRetAnswer(Integer retAnswer) {
        this.retAnswer = retAnswer;
    }

    public String getRetAnswerStr() {
        return retAnswerStr;
    }

    public void setRetAnswerStr(String retAnswerStr) {
        this.retAnswerStr = retAnswerStr;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getHashId() {
        return hashId;
    }

    public void setHashId(String hashId) {
        this.hashId = hashId;
    }

    public String getLiveQuizId() {
        return liveQuizId;
    }

    public void setLiveQuizId(String liveQuizId) {
        this.liveQuizId = liveQuizId;
    }
}
