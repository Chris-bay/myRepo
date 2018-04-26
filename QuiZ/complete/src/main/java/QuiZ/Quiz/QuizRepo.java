package QuiZ.Quiz;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface QuizRepo extends CrudRepository<Quiz, Integer>{
}
