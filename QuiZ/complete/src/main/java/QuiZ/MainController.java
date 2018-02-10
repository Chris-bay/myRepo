package QuiZ;

import QuiZ.Questions.QuestionType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    Quiz q = new Quiz();

    @RequestMapping("/")
    public String indexreroute(){
        return "redirect:index";
    }

    @RequestMapping("/index")
    public String index(){
        String[] s = {"test!", "test..."};
        q.createNewQuestion(QuestionType.MULTIPLECHOICE,"test?", s, 0);
        q.save();
        return "index";
    }


    @RequestMapping("/newQuiz")
    public String newQuiz(){
        return "newQuiz";
    }
    @RequestMapping("/quiz")
    public String Quiz(){
        return "Quiz";
    }
    @RequestMapping("/participate")
    public String Participate(){
        return "Participate";
    }
    @RequestMapping("/login")
    public String login(){
        return "login";
    }


}
