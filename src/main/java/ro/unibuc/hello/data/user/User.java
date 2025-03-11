package ro.unibuc.hello.data.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    
    // Adăugăm lista de ID-uri de carduri de fidelitate
    private List<String> loyaltyCardIds = new ArrayList<>();

    public User() {}

    public User(UserDTO userDTO) {
        this.firstName = userDTO.getFirstName();
        this.lastName = userDTO.getLastName();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public List<String> getLoyaltyCardIds() {
        return loyaltyCardIds;
    }

    public void setLoyaltyCardIds(List<String> loyaltyCardIds) {
        this.loyaltyCardIds = loyaltyCardIds;
    }
    
    public void addLoyaltyCardId(String cardId) {
        if (this.loyaltyCardIds == null) {
            this.loyaltyCardIds = new ArrayList<>();
        }
        this.loyaltyCardIds.add(cardId);
    }
}