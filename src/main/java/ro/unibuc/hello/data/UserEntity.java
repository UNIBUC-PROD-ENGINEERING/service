package ro.unibuc.hello.data;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "users")
public class UserEntity {
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
}
