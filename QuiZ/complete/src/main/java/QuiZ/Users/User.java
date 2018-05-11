package QuiZ.Users;

import org.springframework.data.annotation.Transient;
import sun.security.util.Password;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    Long id;

    String username;
    @Transient
    String password;

    UserRole role;
    Integer points;
    Boolean enabeld;

    public User(String name, UserRole role) {
        this.username = name;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        username = username;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabeld() {
        return enabeld;
    }

    public void setEnabeld(Boolean enabeld) {
        this.enabeld = enabeld;
    }
}

