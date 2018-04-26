package QuiZ.Controller;

import QuiZ.Questions.Question;
import QuiZ.Questions.QuestionRepo;
import QuiZ.Questions.QuestionType;
import QuiZ.Quiz.Quiz;
import QuiZ.Quiz.QuizRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.jws.WebParam;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class MainController {
    @Autowired
    QuestionRepo questionRepo;
    @Autowired
    QuizRepo quizRepo;

    @RequestMapping("/")
    public String indexreroute(){
        return "redirect:index";
    }

    @RequestMapping("/index")
    public String index(){
        /*
        String[] a = {"0", "1", "2", "3"};
        Question q = new Question(QuestionType.MULTIPLECHOICE, "1+1?", a, 2, "");
        questionRepo.save(q);
        System.out.println(questionRepo.findAll().toString());*/
        return "index";
    }


    @RequestMapping("/newQuiz")
    public String newQuiz(Model model){
        model.addAttribute("FormNewQuiz", new FormNewQuiz());
        return "newQuiz";
    }
    @RequestMapping(value = "/newQuiz/add", method = RequestMethod.POST)
    public String addNewQuiz(@ModelAttribute("form") @Valid FormNewQuiz form){

        Quiz q = quizRepo.save(new Quiz(form.getTitle()));
        System.out.println("created new Quiz");
        return "redirect:../changeQuiz/" + q.getId().toString();
    }

    @RequestMapping("/changeQuiz/{id}")
    public String changeQuiz(@PathVariable("id")Integer id, Model model){
        model.addAttribute("FormNewQuestion", new FormNewQuestion());
        FormChangeQuiz formChangeQuiz = new FormChangeQuiz();
        formChangeQuiz.setTitle(quizRepo.findById(id).get().getTitle());
        formChangeQuiz.setId(quizRepo.findById(id).get().getId());
        model.addAttribute("FormChangeQuiz", formChangeQuiz);
        return "changeQuiz";
    }

    @RequestMapping(value = "/changeQuiz/change", method = RequestMethod.POST)
    public String changeQuiz(@ModelAttribute("form") @Valid FormChangeQuiz form, Model model) {
        model.addAttribute("FormNewQuestion", new FormNewQuestion());
        Optional<Quiz> changedQuiz = quizRepo.findById(form.getId());
        changedQuiz.ifPresent(quiz -> quiz.setTitle(form.getTitle()));
        return "redirect:../../quiz";
    }

    @RequestMapping("/deleteQuiz/{id}")
    public String deleteQuiz(@PathVariable("id")Integer id){
        System.out.println("trying to delete QuiZ with id: " + id.toString());
        quizRepo.deleteById(id);
        return "redirect:../quiz";
    }

    @RequestMapping(value = "/addQuestion", method = RequestMethod.POST)
    public String addQuestion(@ModelAttribute("form") @Valid FormNewQuestion form){
        /*System.out.println(form.quizId);
        System.out.println(form.questionText);
        System.out.println(form.answer1);
        System.out.println(form.answer2);
        System.out.println(form.answer3);
        System.out.println(form.answer4);
        System.out.println(form.answer);
        System.out.println(form.media);
        System.out.println(form.points);*/
        form.convertType();
        //System.out.println(form.type);
        String[] answers = {form.answer1, form.answer2, form.answer3, form.answer4};

        Question q = questionRepo.save(new Question(form.type, form.questionText, answers, form.answer, form.media, form.points));
        //System.out.println(q.getId());

        Quiz quiz = quizRepo.findById(form.getQuizId()).get();
        quiz.addQuestion(q.getId());
        quizRepo.save(quiz);

        return "redirect:quiz";
    }

    @RequestMapping("/deleteQuestion/{id}")
    public String deleteQuestion(@PathVariable("id")Integer id){
        System.out.println("trying to delete Question with id: " + id.toString());
        questionRepo.deleteById(id);
        return "redirect:../quiz";
    }

    @RequestMapping("/test")
    public String test(){

        Question q = questionRepo.save(new Question());

        System.out.println(questionRepo.findAll());
        //questionRepo.save(new Question());
        return "redirect:index";
    }
    @RequestMapping("/quiz")
    public String Quiz(){
        return "quiz";
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
