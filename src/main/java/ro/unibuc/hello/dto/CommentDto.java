import java.util.Date;

public class CommentDto {
    private String postId;
    private String userId;
    private String content;      
    private Date createdAt = new Date();

    public CommentDto(){
        
    }
}
