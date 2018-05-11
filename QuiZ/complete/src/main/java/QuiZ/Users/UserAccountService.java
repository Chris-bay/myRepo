package QuiZ.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service("userService")
public class UserAccountService{

    @Autowired
    private UserRepo userRepo;

    public User loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepo.findByUsername(s);
    }

    public void saveUser(User u){
        userRepo.save(u);
    }
}
