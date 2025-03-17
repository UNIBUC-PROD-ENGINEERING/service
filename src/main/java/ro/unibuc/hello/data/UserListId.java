package ro.unibuc.hello.data;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserListId implements Serializable {
    private String userId;
    private String toDoListId;
}
