package ro.unibuc.hello.dto;

import java.util.Date;

public class LikeDto {
    private String userId;      
    private String postId;     
    private String commentId;   
    private Date createdAt = new Date(); 

    public LikeDto(){
        
    }
}
