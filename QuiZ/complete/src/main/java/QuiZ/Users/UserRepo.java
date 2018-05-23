package QuiZ.Users;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepo extends CrudRepository<User, String> {
    public User findByUsername(String name);

    public Optional<User> findById(String id);

    public void removeById(String id);
}
