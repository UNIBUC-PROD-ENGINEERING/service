package ro.unibuc.hello.data;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "requests")
public class RequestEntity {

    @Id
    private RequestId id; 

    @DBRef
    @ToString.Exclude
    private UserEntity user;

    @DBRef
    @ToString.Exclude
    private ToDoListEntity toDoList;

    private String text; 

    public RequestEntity(UserEntity user, ToDoListEntity toDoList, String text) {
        this.id = new RequestId(user.getId(), toDoList.getId());
        this.user = user;
        this.toDoList = toDoList;
        this.text = text;
    }

    @Override
    public String toString() {
        return String.format(
                "RequestEntity[user='%s', toDoList='%s', text='%s']",
                user.getUsername(), toDoList.getName(), text);
    }
}
