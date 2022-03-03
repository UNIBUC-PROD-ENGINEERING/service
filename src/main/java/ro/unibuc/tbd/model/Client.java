package ro.unibuc.tbd.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Client {

    @Id
    public String id;

    public String name;
    public String email;
    public String phoneNumber;
    public String address;
}
