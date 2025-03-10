import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "blockedUsers")
public class BlockedUserEntity {

    @Id
    private String id;             

    private String blockerId;      
    private String blockedUserId;  

    private Date createdAt = new Date();
}
