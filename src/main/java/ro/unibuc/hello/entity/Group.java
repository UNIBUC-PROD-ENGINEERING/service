package ro.unibuc.hello.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;

import java.util.List;

@Document(collection = "groups") // Colectie MongoDB
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString
public class Group {

    @Id
    private String id; // ID unic pentru grup

    private String name; // Numele grupului

    private String createdBy; // ID-ul utilizatorului care a creat grupul

    private List<String> clientIds; // Lista de membri ai grupului (referință la Client)

    private List<String> transactionIds; // Lista tranzacțiilor asociate grupului (referință la Transaction)

    private List<String> pendingInvites;
}
