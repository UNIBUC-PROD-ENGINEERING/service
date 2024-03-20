package ro.unibuc.hello.controllers.contracts;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreateRequest {
    private String id;
    private List<String> policies;
    private List<String> roles;
}
