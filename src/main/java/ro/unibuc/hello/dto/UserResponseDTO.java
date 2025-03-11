package ro.unibuc.hello.dto;

import java.time.Instant;
import java.util.List;

import ro.unibuc.hello.enums.Role;
import ro.unibuc.hello.model.User;

public class UserResponseDTO {
    private String firstName;
    private String lastName;
    private String mail;
    private String phoneNumber;
    private Instant createdAt;
    private List<Role> roles;
    
    public UserResponseDTO(String firstName, String lastName, String mail, String phoneNumber, Instant createdAt, List<Role> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt;
        this.roles = roles;
    }

    public static UserResponseDTO toDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO(
            user.getFirstName(),
            user.getLastName(),
            user.getMail(),
            user.getPhoneNumber(),
            user.getCreatedAt(),
            user.getRoles()
        );
        
        return dto;
    }   
}
