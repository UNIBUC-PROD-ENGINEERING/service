package ro.unibuc.hello.dto.user;

import java.util.ArrayList;
import java.util.List;

import ro.unibuc.hello.enums.Role;
import ro.unibuc.hello.model.User;

public class UserRequestDTO {
    private String firstName;
    private String lastName;
    private String mail;
    private String phoneNumber;
    private String password;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String email) {
        this.mail = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User toEntity() {
        return new User(
            this.firstName,
            this.lastName,
            this.mail,
            this.phoneNumber,
            new ArrayList<>()
        );
    }

    @Override
    public String toString() {
        return "UserRequestDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mail='" + mail + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
