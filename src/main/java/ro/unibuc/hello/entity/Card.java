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
    private String id; 

    private String bankAccountId; 

    private Integer cvv;
}
