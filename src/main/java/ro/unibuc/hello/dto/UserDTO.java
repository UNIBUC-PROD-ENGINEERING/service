package ro.unibuc.hello.dto;

import ro.unibuc.hello.data.UserEntity;

public class UserDTO {
      private String firstName;
     private String userName;

    public UserDTO(){

    }
    public UserDTO(String userName, String firstName)
    {
        this.userName = userName;
        this.firstName = firstName;

    }
}
