package ro.unibuc.hello.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;

@Document(collection = "card")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString
public class Card {
    
    @Id
    private String id; // Use String instead of int for MongoDB compatibility

    private String bankAccountId; // Referință către contul bancar

    //private String abonamentId; // Store reference as String

    private Integer cvv;
}
