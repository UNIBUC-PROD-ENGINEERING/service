package main.java.ro.unibuc.hello.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;
import java.time.LocalDate;

@Document(collection = "basic_plan")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString
public class BasicPlan {

    @Id
    private String id; // Use String instead of int for MongoDB compatibility

    private int price;

    private LocalDate startDate;
}