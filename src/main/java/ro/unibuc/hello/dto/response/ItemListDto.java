package ro.unibuc.hello.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import ro.unibuc.hello.dto.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemListDto {
    private List<ItemDto> requestList;    
}
