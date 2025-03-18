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
@Document(collection = "todo_lists")
@Data
public class ToDoListEntity {

    @Id
    private String id;

    private String name;
    private String description;

    //Pentru a asocierea cu un user
    @DBRef(lazy = true) 
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore 
    private List<UserListEntity> userLists;

    //Pentru asocierea cu un request de join de la alt user
    @DBRef(lazy = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private List<RequestEntity> requests;


    public ToDoListEntity(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public ToDoListEntity(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format(
                "ToDoListEntity[id='%s', name='%s', description='%s', userLists=EXCLUDED_FOR_DEBUGGING]",
                id, name, description);
    }
}
