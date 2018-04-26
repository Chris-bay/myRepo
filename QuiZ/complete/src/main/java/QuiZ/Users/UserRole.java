package QuiZ.Users;

import java.util.HashMap;
import java.util.Map;

public enum UserRole {
    ADMIN(0),
    CREATOR(1),
    PARTICIPANT(2);

    private int number;
    private static Map map = new HashMap<>();

    UserRole(int number){
        this.number = number;
    }

    static {
        for (UserRole qtype : UserRole.values()){
            map.put(qtype.number,qtype);
        }
    }

    public  UserRole getValue(int number){
        return (UserRole) map.get(number);
    }

    public int getNumber() {
        return number;
    }

}
