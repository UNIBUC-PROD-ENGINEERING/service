package ro.unibuc.hello.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestResponseDto {
    String username;
    String toDoList;
    String description;
}
