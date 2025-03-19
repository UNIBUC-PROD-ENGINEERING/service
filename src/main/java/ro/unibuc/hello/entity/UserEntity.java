package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import java.util.Date;

public class UserEntity {

    @Id
    private String id;

    private String username;
    private String password;
    private int tier;
    private Date expirationDate;

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

    @Override
    public String toString() {
        return String.format(
                "User[id='%s', username='%s', tier='%d']",
                id, username, tier);
    }
}
