package ro.unibuc.hello.data;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "books")
public class Book {
    @Id
    private String id;
    private String title;
    private String author;
    private String genre;
    private String availability;
    private Reader borrower;
    private List<Reader> reservers;
    private LocalDate reservationDate;
    private LocalDate borrowDate;

    // Getters
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public String getAvailability() {
        return availability;
    }

    public Reader getBorrower() {
        return borrower;
    }

    public List<Reader> getReservers() {
        return reservers;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public void setBorrower(Reader borrower) {
        this.borrower = borrower;
    }

    public void setReservers(List<Reader> reservers) {
        this.reservers = reservers;
    }

    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }
}
