package ro.unibuc.prodeng.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "components")
public record ComponentEntity(
    @Id
    String id,
    String name,
    String description,
    String category,
    List<String> photoUrls,
    int quantity,
    int availableQuantity,
    boolean isConsumable,
    List<String> tags,
    String infoMD,
    boolean active
    
)