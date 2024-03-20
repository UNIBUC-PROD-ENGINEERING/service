package ro.unibuc.hello.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import ro.unibuc.hello.dtos.ActionDTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Action {

    @Id
    private String code;
    private String description;

    public ActionDTO toDTO() {
        return new ActionDTO(code, description);
    }

    @Override
    public String toString() {
        return String.format(
            "Action[code=%s, description='%s']",
            code, description);
    }
}
