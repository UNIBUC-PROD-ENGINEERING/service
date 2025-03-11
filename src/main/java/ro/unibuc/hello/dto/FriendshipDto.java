package ro.unibuc.hello.dto;

import java.util.Date;

public class FriendshipDto {
    private String userId1;
    private String userId2;
    private String status;
    private Date createdAt = new Date();
    
    public FriendshipDto(){
        
    }
}