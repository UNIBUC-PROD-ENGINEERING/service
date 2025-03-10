package main.java.ro.unibuc.hello.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;

@Document(collection = "transaction")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString
public class Transaction {

    @Id
    private String id; // Use String instead of int for MongoDB compatibility

    private int amount;

    private String recipientId; // Store recipient reference as String ID
}
