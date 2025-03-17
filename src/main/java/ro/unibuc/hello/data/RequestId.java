package ro.unibuc.hello.data;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RequestId implements Serializable {
    private String userId;
    private String toDoListId;
}
