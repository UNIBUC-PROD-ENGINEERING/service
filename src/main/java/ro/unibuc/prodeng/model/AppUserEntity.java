package ro.unibuc.prodeng.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document(collection = "app_users")
public class AppUserEntity {
    @Id
    private String id;
    private String name;
    private String email;
    private String group;
    private Boolean isAdmin;
    private String passwordHash;
}
