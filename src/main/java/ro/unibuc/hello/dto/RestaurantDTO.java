package ro.unibuc.hello.dto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import ro.unibuc.hello.data.RestaurantEntity;
public class RestaurantDTO {
    @Id
    private String id;

    private String name;
    private String email;
    private String address;

    public RestaurantDTO(RestaurantEntity restaurant){
        id = restaurant.getId();
        name = restaurant.getName();
        email = restaurant.getEmail();
        address = restaurant.getAddress();
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return String.format(
                "Restaurant[name='%s', email='%s', address='%s']",
                name, email, address);
    }
}
