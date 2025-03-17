package ro.unibuc.hello.data;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import com.fasterxml.jackson.annotation.JsonIgnore;

@NoArgsConstructor 
@AllArgsConstructor
@Data 
@Document(collection = "user_lists")
public class UserListEntity {

    @Id
    private UserListId id; 

    @DBRef
    @ToString.Exclude
    private UserEntity user; 

    @DBRef
    @ToString.Exclude
    private ToDoListEntity toDoList; 

    private boolean isOwner; 

    public UserListEntity(UserEntity user, ToDoListEntity toDoList, boolean isOwner) {
        this.id = new UserListId(user.getId(), toDoList.getId()); 
        this.user = user;
        this.toDoList = toDoList;
        this.isOwner = isOwner;
    }

    @Override
    public String toString() {
        return String.format(
                "UserListEntity[id='%s', user='%s', toDoList='%s', isOwner='%b']",
                id, user, toDoList, isOwner);
    }
}
