package ro.unibuc.hello.data;
import org.springframework.data.annotation.Id;


public class LocationEntity {

    @Id
    public String id;

    public String address;

    public String name;

    public String phoneNumber;

    public LocationEntity (String address, String name, String phoneNumber){
        this.address = address;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return String.format(
                "Location[address='%s', name='%s', numberOfRooms='%s']",
                id, address, name, phoneNumber);
    }
}
