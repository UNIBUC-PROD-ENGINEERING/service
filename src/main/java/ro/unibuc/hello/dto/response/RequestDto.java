package ro.unibuc.hello.dto.response;

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
public class RequestDto {
    String username;
    String toDoListName;
    String Text;
}
