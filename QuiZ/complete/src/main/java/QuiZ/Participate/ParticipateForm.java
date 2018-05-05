package QuiZ.Participate;

public class ParticipateForm {

    private String name;
    private String id;
    private String errorMessage;

    private Integer number;
    private Integer question;
    private Integer answer;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
