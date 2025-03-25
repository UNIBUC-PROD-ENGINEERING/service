package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

public class FriendRequestEntity {

    @Id
    private String id;

    private String fromUserId;
    private String toUserId;
    private String status; 

    public FriendRequestEntity() {}

    public FriendRequestEntity(String fromUserId, String toUserId, String status) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.status = status;
    }

    public String getId(){
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getToUserId(){
        return toUserId;
    }

    public void setToUserId(String toUserId){
        this.toUserId = toUserId;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }
}

