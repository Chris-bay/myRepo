package QuiZ.Controller;

import QuiZ.LiveQuiz.LiveQuizRepo;
import QuiZ.Questions.Question;
import QuiZ.Questions.QuestionRepo;
import QuiZ.Quiz.Quiz;
import QuiZ.Quiz.QuizRepo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class RestAPI {

    @Autowired
    QuestionRepo questionRepo;
    @Autowired
    QuizRepo quizRepo;
    @Autowired
    LiveQuizRepo liveQuizRepo;

    Gson gson = new Gson();

    @RequestMapping("/api/getAllQuiz")
    @ResponseBody
    public String getAllQuiz(){
        return gson.toJson(quizRepo.findAll());
    }

    @RequestMapping("/api/getAllQuestions")
    @ResponseBody
    public String getAllQuestions(){
        return gson.toJson(questionRepo.findAll());
    }

    @RequestMapping("/api/getQuestionsOfQuiz/{id}")
    @ResponseBody
    public String getQuestionsOfQuiz(@PathVariable("id")Integer id){
        if (quizRepo.findById(id).isPresent()){
            ArrayList<Question> questions = new ArrayList<>();
            for (Integer questionId: quizRepo.findById(id).get().getQuestions()){
                Optional<Question> q = questionRepo.findById(questionId);
                q.ifPresent(questions::add);
            }
            return gson.toJson(questions);
        }else{
            return gson.toJson(null);
        }
    }

    @RequestMapping("/api/getQuestion/{id}")
    @ResponseBody
    public String getQuestion(@PathVariable("id")Integer id){
        return gson.toJson(questionRepo.findById(id).get());
    }

    @RequestMapping("/api/getCurrentQuestion/{id}")
    @ResponseBody
    public String getCurrentQuestion(@PathVariable("id")Integer id){
        Integer momIndex;

        if (quizRepo.findById(id).isPresent()){
            Quiz q = quizRepo.findById(id).get();
            momIndex = q.getQuestions().get(q.getCurrentIndex());
            if (questionRepo.findById(momIndex).isPresent()){
                return gson.toJson(questionRepo.findById(momIndex).get());
            }else {
                return gson.toJson(new Integer[] {});
            }
        }else{
            return gson.toJson(new Integer[] {});
        }
    }

    @RequestMapping("/api/getQuiz/{id}")
    @ResponseBody
    public String getQuiz(@PathVariable("id")Integer id){
        if (quizRepo.findById(id).isPresent()){
            return gson.toJson(quizRepo.findById(id).get());
        }else{
            return gson.toJson(null);
        }
    }

    @RequestMapping("/api/getLiveQuiz/{id}")
    @ResponseBody
    public String getLiveQuiz(@PathVariable("id")Integer id){
        if (liveQuizRepo.findById(id).isPresent()){
            return gson.toJson(liveQuizRepo.findById(id).get());
        }else{
            return gson.toJson(null);
        }
    }

    @RequestMapping("/api/deleteLiveQuiz/{id}")
    @ResponseBody
    public String deleteLiveQuiz(@PathVariable("id")Integer id){
        liveQuizRepo.deleteById(id);
        return gson.toJson(liveQuizRepo.findAll());
    }

    @RequestMapping("/api/getAllLiveQuiz")
    @ResponseBody
    public String getAllLiveQuiz(){
        return gson.toJson(liveQuizRepo.findAll());
    }
}
