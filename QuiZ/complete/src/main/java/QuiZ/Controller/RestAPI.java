package QuiZ.Controller;

import QuiZ.Questions.QuestionRepo;
import QuiZ.Quiz.QuizRepo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RestAPI {

    @Autowired
    QuestionRepo questionRepo;
    @Autowired
    QuizRepo quizRepo;

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
            return gson.toJson(quizRepo.findById(id).get().getQuestions());
        }else{
            return gson.toJson(null);
        }
    }

    @ResponseBody
    public String getQuiz(@PathVariable("id")Integer id){
        if (quizRepo.findById(id).isPresent()){
            return gson.toJson(quizRepo.findById(id).get());
        }else{
            return gson.toJson(null);
        }
    }

}
