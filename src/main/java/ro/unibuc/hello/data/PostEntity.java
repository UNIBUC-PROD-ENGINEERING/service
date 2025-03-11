package ro.unibuc.hello.data;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@Document(collection="posts")
public class PostEntity {
    @Id
    private String id;

    @Field
    private String userId;

    private String content;
    private String mediaUrl;

    private Date createdAt = new Date();
    private Date updatedAt = null;

    public enum PostVisibility {
        PUBLIC,
        FRIENDS_ONLY
    }

    private PostVisibility visibility;

    
    public PostEntity(){

    }
}
