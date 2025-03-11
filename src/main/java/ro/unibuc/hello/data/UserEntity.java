package ro.unibuc.hello.data;

import java.util.Date;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.AccessLevel;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class UserEntity{
    @Id
    private String id;

    @Indexed(unique = true)
    @Getter @Setter private String username;

    @Indexed(unique = true)
    @Getter @Setter private String email;

    private String passwordHash;
    @Getter @Setter private String name;
    private Date dateOfBirth;
    private String bio;
    private String profilePicture;
    private Date createdAt = new Date();

}