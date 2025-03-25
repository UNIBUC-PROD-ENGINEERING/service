package ro.unibuc.hello.dto;

public class FriendRequest {

    private String id;
    private String fromUserId;
    private String toUserId;
    private String status; 

    public FriendRequest() {}

    public FriendRequest(String id, String fromUserId, String toUserId, String status) {
        this.id = id;
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
