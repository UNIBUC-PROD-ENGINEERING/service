package ro.unibuc.tbd.model;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Order {

    @Id
    public String id;

    public String clientId;
    public List<String> meals;
    public Float totalPrice;
}
