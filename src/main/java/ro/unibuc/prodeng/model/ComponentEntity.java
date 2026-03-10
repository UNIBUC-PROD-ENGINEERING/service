package ro.unibuc.prodeng.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Data // Generates getters, setters, to string, equals and hashcode
@NoArgsConstructor //Generates the an empty constructor
@Document(collection = "components")
public class ComponentEntity {

    @Id
    private String id;
    private String name;
    private String description;
    private String category;
    private List<String> photoUrls;
    private Integer quantity;
    private Integer availableQuantity;
    private Boolean isConsumable;
    private List<String> tags;
    private String infoMarkdown;
    private Instant createdAt = Instant.now(); 


    public void setQuantity(Integer quantity) {
        if (quantity != null && this.availableQuantity != null && this.availableQuantity > quantity) {
            throw new IllegalArgumentException("Total quantity cannot be set lower than the currently available quantity.");
        }
        this.quantity = quantity;
    }

    public void setAvailableQuantity(Integer availableQuantity) {
        if (availableQuantity != null && this.quantity != null && availableQuantity > this.quantity) {
            throw new IllegalArgumentException("Available quantity cannot exceed total quantity.");
        }
        this.availableQuantity = availableQuantity;
    }
}