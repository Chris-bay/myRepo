package QuiZ.Quiz;

import QuiZ.Questions.*;
import com.sun.istack.internal.Nullable;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Optional;

@Entity
public class Quiz {

    //<Question> questions = new ArrayList<>();

    @Autowired
    public ArrayList<Question> questions = new ArrayList<>();


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String title;

    public Question addQuestion(Question q){
        questions.add(q);
        return q;
    }

    public boolean hasQuestion(Question question){
        for (Question q : questions){
            if (q.equals(question)){
                return true;
            }
        }
        return false;
    }

    public Quiz(String title){
        this.title = title;
    }


/*
    public Optional<Question> findQuestion(Integer id){
        Optional<Question> retQuestion = Optional.empty();
        for (Question q:questions){
            if (Objects.equals(q.getId(), id)){
                retQuestion = Optional.of(q);
            }
        }
        return retQuestion;
    }
*/
    public Optional<Question> changeQuestion(Integer id, Question question){
        Optional<Question> foundQuestion = Optional.empty();
        for (Question q:questions) {
            if (q.getId().equals(id)){
                int index = questions.indexOf(q);
                questions.remove(index);
                questions.add(question);
                foundQuestion = Optional.of(q);
            }
        }
        return foundQuestion;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public Quiz(){
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
