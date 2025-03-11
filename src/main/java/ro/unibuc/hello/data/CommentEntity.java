package ro.unibuc.hello.data;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;


@Data
@Document(collection = "comments") 
public class CommentEntity {
    @Id
    private String id;   
    
    @Field("postId")
    private String postId;

    @Field("userId")
    private String userId;

    private String content;      
    private Date createdAt = new Date();
}
