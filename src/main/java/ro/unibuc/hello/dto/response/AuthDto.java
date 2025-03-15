package ro.unibuc.hello.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.unibuc.hello.data.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthDto {
    private String username;
    private String email;
    private Role role;
    private String token;
}