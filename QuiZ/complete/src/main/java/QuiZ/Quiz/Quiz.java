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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String title;

    private ArrayList<Integer> questionIds = new ArrayList<Integer>();

    public Integer addQuestion(Integer q){
        this.questionIds.add(q);
        return q;
    }

    public boolean hasQuestion(Integer questionId){
        for (Integer q : questionIds){
            if (q.equals(questionId)){
                return true;
            }
        }
        return false;
    }

    public Quiz(String title){
        this.title = title;
        this.questionIds = new ArrayList<Integer>();
    }

    public ArrayList<Integer> getQuestions() {
        return questionIds;
    }

    public Quiz(){
        this.questionIds = new ArrayList<Integer>();
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
