package ro.unibuc.hello.data;

import lombok.*;

import java.util.Collection;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user_lists")
@Data
public class UserListEntity {

    @Id
    private String id; 

    private String username;
    private String toDoList;

    private boolean isOwner; 

    public UserListEntity(String user, String toDoList, boolean isOwner) {
        this.id = user + toDoList; 
        this.username = user;
        this.toDoList = toDoList;
        this.isOwner = isOwner;
    }

    public boolean getIsOwner() {
        return isOwner;
    }

   
    public void setIsOwner(boolean isOwner) {
        this.isOwner = isOwner;
    }

    @Override
    public String toString() {
        return String.format(
                "UserListEntity[id='%s', user='%s', toDoList='%s', isOwner='%b']",
                id, username, toDoList, isOwner);
    }
}
