package ro.unibuc.hello.dtos;

import lombok.*;
import ro.unibuc.hello.entities.User;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private String id;
    private List<PolicyDTO> policies;
    private List<RoleDTO> roles;

    public User toUser() {
        return new User(id, policies.stream().map(PolicyDTO::toPolicy).collect(Collectors.toList()), roles.stream().map(RoleDTO::toRole).collect(Collectors.toList()));
    }
}
