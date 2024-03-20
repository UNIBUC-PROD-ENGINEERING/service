package ro.unibuc.hello.controllers.contracts;


import lombok.*;
import ro.unibuc.hello.entities.Statement;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PolicyCreateRequest  {
    private String name;
    private List<Statement> statements;
}
