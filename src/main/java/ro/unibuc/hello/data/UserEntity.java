package ro.unibuc.hello.data;

import lombok.*;

import java.util.Collection;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
@Data
public class UserEntity implements UserDetails{
    @Id
    private String id;

    private String username;

    private String email;

    private String password;

    private String role;

    public Role getRole(){
    return Role.valueOf(role);
    }

    public void setRole(Role role){
        this.role = role.name();
    }

    public UserEntity(String username, String email, String password){
        this.username=username;
        this.email=email;
        this.password=password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of((GrantedAuthority) () ->"ROLE_"+ getRole());
    }
}
