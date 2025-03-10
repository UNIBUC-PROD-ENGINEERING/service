package main.java.ro.unibuc.hello.entity;

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
    private String id; // Use String instead of int for MongoDB compatibility

    private String name;

    private String phoneNumber;

    private String email;

    private String password;

    private List<String> bankAccountIds = new ArrayList<>(); // Store references as String IDs

    private List<String> transactionIds = new ArrayList<>(); // Store references as String IDs
}