package ro.unibuc.hello.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;

@Document(collection = "transaction")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString
public class Transaction {

    @Id
    private String id;

    private double amount; // Use double for financial transactions

    private String fromAccountId; // Sender's bank account ID

    private String toAccountId; // Receiver's bank account ID
}
