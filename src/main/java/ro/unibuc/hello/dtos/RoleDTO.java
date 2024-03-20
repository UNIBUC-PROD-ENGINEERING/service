package ro.unibuc.hello.dtos;

import lombok.*;
import ro.unibuc.hello.entities.Role;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleDTO {
    private String id;
    private List<PolicyDTO> policies;

    public Role toRole() {
        return new Role(id, policies.stream().map(PolicyDTO::toPolicy).collect(Collectors.toList()));
    }
}
