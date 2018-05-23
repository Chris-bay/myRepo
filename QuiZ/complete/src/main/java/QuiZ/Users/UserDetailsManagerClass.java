package QuiZ.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;

import java.util.Optional;

public class UserDetailsManagerClass implements UserDetailsManager {

    @Autowired
    UserRepo userRepo;
    @Autowired
    UserNames userNames;

    @Override
    public void createUser(UserDetails userDetails) {
        userRepo.save(UserDetailsToUser(userDetails));
        userNames.add(userDetails.getUsername());
    }

    public void createUser(UserDetails userDetails, UserRole role, Integer points, Boolean enabled) {
        userRepo.save(UserDetailsToUser(userDetails));
        userNames.add(userDetails.getUsername());

    }

    @Override
    public void updateUser(UserDetails userDetails) {

    }

    public void changeUsername(String username, String newUsername){
        User u = userRepo.findByUsername(username);
        u.setUsername(newUsername);
        userNames.ChangeUserName(username, newUsername);
        userRepo.removeById(username);
        userRepo.save(u);
    }

    @Override
    public void deleteUser(String s) {
        Optional<User> u = userRepo.findById(s);
        userRepo.removeById(s);
        u.ifPresent(user -> userNames.remove(user.getUsername()));
    }

    @Override
    public void changePassword(String s, String s1) {
        if(userRepo.findById(s).isPresent()){
            User u = userRepo.findById(s).get();
            u.setPassword(s1);
            userRepo.removeById(s);
            userRepo.save(u);
        }
    }

    @Override
    public boolean userExists(String s) {
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }

    private User UserDetailsToUser(UserDetails userDetails){
        return new User(userDetails.getUsername(), userDetails.getPassword(), UserRole.PARTICIPANT);
    }

    private User UserDetailsToUser(UserDetails userDetails, UserRole role, Integer points, Boolean enabled){
        User u = new User(userDetails.getUsername(), userDetails.getPassword(), role);
        u.setEnabeld(enabled);
        u.setPoints(points);
        return u;
    }

}

