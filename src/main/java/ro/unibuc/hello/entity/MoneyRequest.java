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

    private String fromAccountId; 

    private String toAccountId; 

    private double amount;

    private String status; 
}
