package ro.unibuc.hello.data;

import lombok.*;

import java.util.Collection;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "users")
public class UserEntity implements UserDetails{
    @Id
    private String id;

    private String username;

    private String email;

    private String password;

    public UserEntity(String username, String email, String password){
        this.username=username;
        this.email=email;
        this.password=password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of((GrantedAuthority) () ->"ROLE_"+ role.name());
    }
}
