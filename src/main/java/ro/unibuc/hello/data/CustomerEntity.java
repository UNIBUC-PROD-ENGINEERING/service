package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class CustomerEntity {

    @Id
    public String id;

    public String name;

    public Integer age;

    @DBRef
    public TicketEntity ticket;

    public CustomerEntity(String name, Integer age, TicketEntity ticket) {
        this.ticket = ticket;
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[name='%s', age='%d', ticket='%s']",
                id, name, age, ticket);
    }
}
