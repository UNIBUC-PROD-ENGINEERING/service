package ro.unibuc.hello.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;

@Document(collection = "money_requests")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString
public class MoneyRequest {

    @Id
    private String id;

    private String fromAccountId; // The sender who is requesting money

    private String toAccountId; // The recipient who is asked to send money

    private double amount;

    private String status; // "PENDING", "APPROVED", "DECLINED"
}
