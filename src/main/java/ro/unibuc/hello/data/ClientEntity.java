package ro.unibuc.hello.data;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class ClientEntity {
    
    @Id
    public String id;

    public String fullName;
    public String favouriteBook;

    @DBRef
    private List<BookEntity> books;
    
    public ClientEntity() {}

    public ClientEntity(String fullName, String favouriteBook) {
        this.fullName = fullName;
        this.favouriteBook = favouriteBook;
    }

    @Override
    public String toString() {
        return "ClientEntity{" +
                "id='" + id + '\'' +
                ", fullName='" + fullName + '\'' +
                ", favouriteBook='" + favouriteBook + '\'' +
                ", books='" + books.toString() + '\'' +
                '}';
    }
}