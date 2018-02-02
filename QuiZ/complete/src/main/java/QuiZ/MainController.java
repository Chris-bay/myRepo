package QuiZ;

import org.springframework.beans.factory.annotation.Autowired;
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
        q.createNewQuestion("test?", s);
        q.save();
        return "index";
    }

    @RequestMapping("/newQuiz")
    public String newQuiz(){
        return "newQuiz";
    }
    @RequestMapping("/newQuiz")
    public String Quiz(){
        return "Quiz";
    }
    @RequestMapping("/newQuiz")
    public String Participate(){
        return "Participate";
    }
    @RequestMapping("/login")
    public String login(){
        return "login";
    }


}
