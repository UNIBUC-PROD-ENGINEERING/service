import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "likes")
public class LikeEntity {

    @Id
    private String id;          

    private String userId;      

    private String postId;     
    private String commentId;   

    private Date createdAt = new Date(); 
}
