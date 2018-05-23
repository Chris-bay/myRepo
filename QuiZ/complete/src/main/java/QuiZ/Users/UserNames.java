package QuiZ.Users;

import java.util.ArrayList;

public class UserNames {
    private ArrayList<String> usernames = new ArrayList<>();

    public boolean UserNameExists(String username){
        return usernames.contains(username);
    }

    public boolean has(String username){
        return UserNameExists(username);
    }

    public boolean ChangeUserName(String oldUserName, String newUserName){
        if (usernames.contains(oldUserName)){
            usernames.add(newUserName);
            usernames.remove(oldUserName);
            return true;
        }else{
            return false;
        }
    }

    public void remove(String username){
        usernames.remove(username);
    }

    public void add(String username){
        usernames.add(username);
    }

}
