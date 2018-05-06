package QuiZ.Controller;

import QuiZ.Participate.ParticipateForm;
import QuiZ.Questions.Question;
import QuiZ.Questions.QuestionRepo;
import QuiZ.Questions.QuestionType;
import QuiZ.Quiz.Quiz;
import QuiZ.Quiz.QuizRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.jws.WebParam;
import javax.validation.Valid;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
public class MainController {
    @Autowired
    QuestionRepo questionRepo;
    @Autowired
    QuizRepo quizRepo;

    Boolean redirect = false;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/")
    public String indexreroute(){
        return "redirect:index";
    }

    @RequestMapping("/index")
    public String index(){
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
        log.info("created new Quiz");
        return "redirect:../changeQuiz/" + q.getId().toString();
    }

    @RequestMapping("/changeQuiz/{id}")
    public String changeQuiz(@PathVariable("id")Integer id, Model model){
        model.addAttribute("FormNewQuestion", new FormNewQuestion());
        FormChangeQuiz formChangeQuiz = new FormChangeQuiz();
        formChangeQuiz.setTitle(quizRepo.findById(id).get().getTitle());
        formChangeQuiz.setId(quizRepo.findById(id).get().getId());
        model.addAttribute("FormChangeQuiz", formChangeQuiz);
        model.addAttribute("FormChangeQuestion", new FormChangeQuestion());
        return "changeQuiz";
    }

    @RequestMapping(value = "/changeQuiz/change", method = RequestMethod.POST)
    public String changeQuiz(@ModelAttribute("form") @Valid FormChangeQuiz form, Model model) {
        model.addAttribute("FormNewQuestion", new FormNewQuestion());
        Optional<Quiz> changedQuiz = quizRepo.findById(form.getId());
        changedQuiz.ifPresent(quiz -> {
            quiz.setTitle(form.getTitle());
            quizRepo.save(quiz);
        });
        return "redirect:../changeQuiz/" + form.getId();
    }

    @RequestMapping("/deleteQuiz/{id}")
    public String deleteQuiz(@PathVariable("id")Integer id){
        log.info("trying to delete QuiZ with id: " + id.toString());
        if (quizRepo.findById(id).isPresent()){
            for (Integer i : quizRepo.findById(id).get().getQuestions()) {
                //questionRepo.deleteById(i);
                if (questionRepo.findById(i).isPresent()){
                    questionRepo.deleteById(i);
                }else{
                    log.warn("Could not delete Question with Id: [" + i + "] (Question not found)");
                }
            }
            quizRepo.deleteById(id);
        }else{
            log.warn("Could not delete Quiz with Id: [" + id + "] (Quiz not found)");
        }
        return "redirect:../quiz";
    }

    @RequestMapping(value = "/addQuestion", method = RequestMethod.POST)
    public String addQuestion(@ModelAttribute("form") @Valid FormNewQuestion form, Model model){
        form.convertType();

        String[] answers = {form.answer1, form.answer2, form.answer3, form.answer4};

        Question q = questionRepo.save(new Question(form.type, form.questionText, answers, answers[Integer.parseInt(form.answer)], form.media, form.points));

        Quiz quiz = quizRepo.findById(form.getQuizId()).get();
        quiz.addQuestion(q.getId());
        quizRepo.save(quiz);
        redirect = true;
        return "redirect:changeQuiz/" + form.getQuizId().toString();
    }

    @RequestMapping("/deleteQuestion/{quizId}/{questionId}")
    public String deleteQuestion(@PathVariable("quizId")Integer quizId, @PathVariable("questionId")Integer questionId){
        log.info("trying to delete Question with id: " + questionId.toString());
        try {
            questionRepo.deleteById(questionId);
            if(quizRepo.findById(quizId).isPresent()){
                Quiz q = quizRepo.findById(quizId).get();
                q.getQuestions().remove(q.getQuestions().indexOf(questionId));
                quizRepo.save(q);
            }else{
                log.warn("Could not delete Quiz with Id [" + quizId + "] (Quiz not found)");
            }
        }catch (EmptyResultDataAccessException e){
            log.warn("Could not delete question with Id [" + questionId + "] (Question not found)");
        }
        return "redirect:../../quiz";
    }

    @RequestMapping(value = "/changeQuestion", method = RequestMethod.POST)
    public String changeQuestion(@ModelAttribute("form") @Valid FormChangeQuestion form, Model model) {
        System.out.println(form.toString());
        if (questionRepo.findById(form.questionId).isPresent()){
            Question q = questionRepo.findById(form.questionId).get();
            form.convertType();
            form.convertAnswers();
            q.setPoints(form.getPoints());

            // (M)MULTIPLECHOICE Questions returns the correct answer as index of answers

            if (form.getType() == QuestionType.MMULTIPLECHOICE || form.getType() == QuestionType.MULTIPLECHOICE){
                q.setAnswer(form.getAnswers()[Integer.parseInt(form.getAnswer())]);
            }else{
                q.setAnswer(form.getAnswer());
            }
            q.setAnswers(form.getAnswers());
            q.setMedia(form.getMedia());
            q.setQuestionText(form.getQuestionText());
            q.setType(form.getType());
            q.setOrder(form.getOrder());
            questionRepo.save(q);
        }else{
            log.warn("Could not delete question with Id [" + form.questionId + "] (Question not found)");
        }
        return changeQuiz(form.getQuizId(), model);
    }

    @RequestMapping("/quiz")
    public String Quiz(){
        return "quiz";
    }

    @RequestMapping(value = "/startParticipate", method = RequestMethod.GET)
    public String participateGet(Model model){
        model.addAttribute("ParticipateForm", new ParticipateForm());
        return "startParticipate";
    }

    @RequestMapping(value = "/participate", method = RequestMethod.POST)
    public String participatePost(@ModelAttribute("form") @Valid ParticipateForm form, Model model,RedirectAttributes redir){
        ModelAndView modelAndView = new ModelAndView();
        if (quizRepo.findById(Integer.parseInt(form.getQuizId())).isPresent()){
            model.addAttribute("ParticipateForm", form);
            return "participate";
            //return "redirect:participate/" + form.getId();
        }else {
            form.setErrorMessage("Could not find the given Quiz");
            model.addAttribute("ParticipateForm", form);
            return "startParticipate";
        }
    }

    @RequestMapping(value = "/participate/submit", method = RequestMethod.POST)
    public String submitAnswer(@ModelAttribute("form") @Valid ParticipateForm form, Model model){
        model.addAttribute("ParticipateForm", form);
        return "participateQResult";
    }

    @RequestMapping("/next/{id}")
    public String next(@PathVariable("id") String id, @ModelAttribute("form") @Valid ParticipateForm form, Model model){
        if (quizRepo.findById(Integer.parseInt(id)).isPresent()) {
            Quiz q = quizRepo.findById(Integer.parseInt(id)).get();
            q.setCurrentIndex(q.getCurrentIndex()+1);
            quizRepo.save(q);
        }
        model.addAttribute("ParticipateForm", form);
        return "redirect:participate/" + form.getQuizId();
    }

    @RequestMapping("/participate/{id}")
    public String participateId(@PathVariable("id")String id){
        return "participate";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }
}
