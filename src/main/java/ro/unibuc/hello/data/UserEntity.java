package ro.unibuc.hello.data;

import java.sql.Date;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

public class UserEntity {

    @Id
    public String id;
    public String lastName;
    public String firstName;
    public Integer age;
    public String userName;

    public UserEntity() {
    }

    public UserEntity(String lastName, String firstName, Integer age, String userName) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.age = age;
        this.userName = userName;
    }

    @Override
    public String toString() {
        return String.format(
                "User[lastName='%s', firstName='%s', age='%s', userName='%s']",
                lastName, firstName, age, userName);
    }
    
}