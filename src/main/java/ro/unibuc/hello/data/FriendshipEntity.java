package ro.unibuc.hello.data;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Data
@Document(collection = "friendships")
public class FriendshipEntity {

    @Id
    private String id;          

    private String userId1;
    private String userId2;

    private FriendshipStatus status;

    private Date createdAt = new Date();

    public enum FriendshipStatus {
        PENDING,
        ACCEPTED,
        BLOCKED
    }; 

} 
