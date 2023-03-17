package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class UserEntity {

    @Id
    public String id;
    public String firstName;

    public String lastName;

    public String userName;
    public String password;

    public UserEntity(String firstName, String lastName,String userName, String password)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
    }

    public String getName(){
        return String.format("firstName='%s', lastName='%s'", firstName, lastName);
    }
    public void setUserName(String userName){
        this.userName = userName;
    }
    public String getFirstName(){
        return this.firstName;
    }
    public String getLastName(){
        return this.lastName;
    }
    public String getUserName(){
        return this.userName;
    }
    public String getPassword(){
        return this.password;
    }


}
