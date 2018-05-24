package QuiZ.LiveQuiz;

import QuiZ.Quiz.Quiz;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

@Entity
public class LiveQuiz {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToOne
    private Quiz quiz;
    private Integer participantsNumber;
    private ArrayList<String> participants = new ArrayList<>();
    private LocalDateTime dateOfActivation;
    private String hashId;

    public LiveQuiz(){}

    public LiveQuiz(Quiz q){
        this.quiz = q;
        this.dateOfActivation = LocalDateTime.now();
    }

    public LiveQuiz(Quiz q, String hashid){
        this.quiz = q;
        this.hashId = hashid;
        this.dateOfActivation = LocalDateTime.now();
    }

    public ArrayList<String> addParticipant(String name){
        this.participants.add(name);
        return this.participants;
    }

    public Integer getParticipantsNumber() {
        return participantsNumber;
    }

    public void setParticipantsNumber(Integer participantsNumber) {
        this.participantsNumber = participantsNumber;
    }

    public ArrayList<String> getParticipants() {
        return participants;
    }

    public void setParticipants(ArrayList<String> participants) {
        this.participants = participants;
    }

    public Integer getId() {
        return id;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public LocalDateTime getDateOfActivation() {
        return dateOfActivation;
    }

    public String getHashId() {
        return hashId;
    }

    public void setHashId(String hashId) {
        this.hashId = hashId;
    }
}
