package main.java.ro.unibuc.hello.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;

@Document(collection = "bank_account")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString
public class BankAccount {

    @Id
    private String id;  // Use String instead of int for MongoDB compatibility

    private String name;

    private String IBAN;

    private int balance;

    private String clientId; // Store Client reference as a String (MongoDB does not use @ManyToOne)
}
