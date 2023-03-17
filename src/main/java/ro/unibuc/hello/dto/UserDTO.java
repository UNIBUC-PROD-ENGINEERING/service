package ro.unibuc.hello.dto;

import ro.unibuc.hello.data.UserEntity;

public class UserDTO {
    private String id;
    private String firstName;
    private String userName;

    public UserDTO(){

    }
    public UserDTO(String id, String userName, String firstName)
    {
        this.id = id;
        this.userName = userName;
        this.firstName = firstName;

    }

    public String getFirstName() {
        return firstName;
    }

    public String getUserName() {
        return userName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                '}';
    }
}
