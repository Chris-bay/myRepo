package QuiZ;

import QuiZ.Questions.Question;
import QuiZ.Questions.QuestionRepo;
import QuiZ.Questions.QuestionType;
import QuiZ.Quiz.Quiz;
import QuiZ.Quiz.QuizRepo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
