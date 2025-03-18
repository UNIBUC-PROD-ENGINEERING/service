package ro.unibuc.hello.data;

import lombok.*;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
