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

    private int amount;

    private String recipientId;
}
