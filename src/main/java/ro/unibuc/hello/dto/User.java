package ro.unibuc.hello.dto;
import org.springframework.data.annotation.Id;

import java.util.UUID;


public class User {
    @Id
    public String userId;

    public String firstName;
    public String lastName;

    public User(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId(String id){
        this.userId=id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
