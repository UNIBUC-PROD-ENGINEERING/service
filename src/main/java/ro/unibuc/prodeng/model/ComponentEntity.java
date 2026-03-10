package ro.unibuc.prodeng.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "components")
public class ComponentEntity(
    @Id
    private String id,
    private String name,
    private String description,
    private String category,
    private List<String> photoUrls,
    private int quantity,
    private int availableQuantity,
    private boolean isConsumable,
    public List<String> tags,
    public String infoMD,
    public boolean active
    
)