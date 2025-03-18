package ro.unibuc.hello.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;

@Document(collection = "bank_account")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString
public class BankAccount {

    @Id
    private String id;  

    private String name;

    private String IBAN;

    private double balance;

    private String clientId; 

    public void updateBalance(double amount) {
        this.balance += amount;
    }
}
