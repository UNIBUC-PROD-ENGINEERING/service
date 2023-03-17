package ro.unibuc.hello.dto;

import ro.unibuc.hello.data.UserEntity;

public class RegisterUserDTO {
    public String firstName;

    public String lastName;

    public String userName;
    public String password;

    public RegisterUserDTO(UserEntity userEntity)
    {
        this.firstName = userEntity.getFirstName();
        this.lastName = userEntity.getLastName();
        this.userName = userEntity.getUserName();
        this.password = userEntity.getPassword();
    }

}
