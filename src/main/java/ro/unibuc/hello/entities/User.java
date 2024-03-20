package ro.unibuc.hello.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import ro.unibuc.hello.dtos.UserDTO;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    private String id;
    private List<Policy> policies;
    private List<Role> roles;
    @Override
    public String toString() {
        return String.format("User[id=%s]", id);
    }

    public UserDTO toDTO() {
        return new UserDTO(id, policies.stream().map(Policy::toDTO).collect(Collectors.toList()), roles.stream().map(Role::toDTO).collect(Collectors.toList()));
    }
}
