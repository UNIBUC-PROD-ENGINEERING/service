package ro.unibuc.hello.data.user;

import java.util.List;

public class UserDTO {
    private String firstName;
    private String lastName;
    private List<String> loyaltyCardIds;

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

    public UserDTO(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserDTO() {
    }
}