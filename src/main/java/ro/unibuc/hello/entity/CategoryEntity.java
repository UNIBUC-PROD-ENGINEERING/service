package ro.unibuc.hello.entity;

import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryEntity {
    @Id
    private String id;
    private String name;

    public CategoryEntity(String name) {
        this.name = name;
    }
}
