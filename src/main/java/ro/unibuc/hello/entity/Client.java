package ro.unibuc.hello.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;
import java.util.List;
import java.util.ArrayList;

@Document(collection = "client")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString
public class Client {

    @Id
    private String id; 

    private String name;

    private String phoneNumber;

    private String email;

    private String password;

    private List<String> bankAccountIds = new ArrayList<>();

    private List<String> transactionIds = new ArrayList<>(); 
}