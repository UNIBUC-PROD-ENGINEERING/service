package ro.unibuc.tbd.model;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Meal {

    @Id
    public String id;

    public String name;
    public List<String> ingredients;
    public Integer portionSize;
    public Float price;
}
