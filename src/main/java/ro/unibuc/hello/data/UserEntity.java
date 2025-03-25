package ro.unibuc.hello.data;

import lombok.*;

import java.util.Collection;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.mongodb.core.mapping.DBRef;

@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
@Data
@Builder
@Getter
public class UserEntity implements UserDetails{
    @Id
    private String id;

    @TextIndexed
    private String username;

    private String email;

    private String password;

    private String role;

    //Pentru asocierea cu un toDoList
    @DBRef(lazy = true) 
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore 
    private List<UserListEntity> userLists;

    //Pentru a da request de join la alte toDoLists
    @DBRef(lazy = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private List<RequestEntity> requests;


    public Role getRole(){
        return Role.valueOf(role);
    }

    public void setRole(Role role){
        this.role = role.name();
    }

    public UserEntity(String username, String email, String password, Role role){
        this.username=username;
        this.email=email;
        this.password=password;
        this.setRole(role);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of((GrantedAuthority) () ->"ROLE_"+ getRole());
    }
}
