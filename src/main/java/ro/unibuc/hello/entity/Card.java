package main.java.ro.unibuc.hello.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;

@Document(collection = "card")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString
public class Card extends BankAccount {

    @Id
    private String id; // Use String instead of int for MongoDB compatibility

    private String abonamentId; // Store reference as String (MongoDB does not use @Enumerated)

    private int cvv;
}