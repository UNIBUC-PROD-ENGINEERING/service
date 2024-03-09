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

    // book entity was not working without getters (and setters) so adding them here too
    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getFavouriteBook() {
        return favouriteBook;
    }

    public List<BookEntity> getBooks() {
        return books;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setFavouriteBook(String favouriteBook) {
        this.favouriteBook = favouriteBook;
    }

    public void setBooks(List<BookEntity> books) {
        this.books = books;
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