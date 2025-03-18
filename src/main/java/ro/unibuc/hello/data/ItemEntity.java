package ro.unibuc.hello.data;

import lombok.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "items")
@Data
public class ItemEntity {

    @Id
    private String id;

    private String name;
    private String description;
    private String todoList;

    public ItemEntity(String name, String description, String todoList) {
        this.name = name;
        this.description = description;
        this.todoList = todoList;
    }

    @Override
    public String toString() {
        return String.format(
                "Item[id='%s', name='%s', description='%s']",
                id, name, description);
    }
}
