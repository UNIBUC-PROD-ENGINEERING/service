package ro.unibuc.hello.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import ro.unibuc.hello.dtos.RoleDTO;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {
    @Id
    private String id;
    private List<Policy> policies;

    @Override
    public String toString() {
        return String.format("Role[id=%s]", id);
    }

    public RoleDTO toDTO() {
        return new RoleDTO(id, policies.stream().map(Policy::toDTO).collect(Collectors.toList()));
    }
}
