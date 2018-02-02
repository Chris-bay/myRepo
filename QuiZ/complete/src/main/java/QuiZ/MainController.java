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
    public String charts(){
        return "newQuiz";
    }

    @RequestMapping("/login")
    public String tables(){
        return "login";
    }
}
