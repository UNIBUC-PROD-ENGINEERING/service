package ro.unibuc.hello.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Role {
    @Id private String id;

    private Actor actor;
    private Movie movie;
}
