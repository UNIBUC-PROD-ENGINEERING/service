package ro.unibuc.hello.data;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection="user")
public class User {
    @Id
    private String username;

    private String email;

    private String password;
}
