package QuiZ.Users;

import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Long> {
    public User findByUsername(String name);
}
