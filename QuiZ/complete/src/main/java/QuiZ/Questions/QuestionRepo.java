package QuiZ.Questions;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface QuestionRepo extends CrudRepository<Question, Integer>{
}
