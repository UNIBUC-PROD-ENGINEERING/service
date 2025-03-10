package main.java.ro.unibuc.hello.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;

@Document(collection = "premium_plan")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString
public class PremiumPlan extends BasicPlan {

    @Id
    private String id; // Use String instead of int for MongoDB compatibility

    private int cashback;
}