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
@Document(collection = "requests")
@Data
public class RequestEntity {

    @Id
    private String id; 

    private String username;
    private String toDoList;

    private String text; 

    public RequestEntity(String user, String toDoList, String text) {
        this.id = user + toDoList;
        this.username = user;
        this.toDoList = toDoList;
        this.text = text;
    }

    @Override
    public String toString() {
        return String.format(
                "RequestEntity[user='%s', toDoList='%s', text='%s']",
                username, toDoList, text);
    }
}
