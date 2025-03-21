package ro.unibuc.hello.data;

import ro.unibuc.hello.dto.Notification;

import org.springframework.data.annotation.Id;
import java.util.Date;
import java.util.List;

public class UserEntity {

    @Id
    private String id;

    private String username;
    private String password;
    private int tier;
    private Date expirationDate;
    private List<Notification> notifications;


    public UserEntity() {}

    public UserEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username= username;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public int getTier(){
        return tier;
    }

    public void setTier(int tier){
        this.tier = tier;
    }

    public Date getExpirationDate(){
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate){
        this.expirationDate = expirationDate;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    @Override
    public String toString() {
        return String.format(
                "User[id='%s', username='%s', tier='%d']",
                id, username, tier);
    }
}
