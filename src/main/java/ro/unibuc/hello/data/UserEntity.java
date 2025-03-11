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
    @Getter @Setter private String id;

    @Indexed(unique = true)
    @Getter @Setter private String username;

    @Indexed(unique = true)
    @Getter @Setter private String email;

    @Getter @Setter private String passwordHash;
    @Getter @Setter private String name;
    @Getter @Setter private Date dateOfBirth;
    @Getter @Setter private String bio;
    @Getter @Setter private String profilePicture;
    private Date createdAt = new Date();

}