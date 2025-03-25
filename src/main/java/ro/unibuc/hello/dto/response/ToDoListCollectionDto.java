package ro.unibuc.hello.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToDoListCollectionDto {
    private List<ToDoListResponseDto> toDoListCollection;
}
