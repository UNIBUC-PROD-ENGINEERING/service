package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Document
public class LoanEntity {
    @Id
    private String id;

    @DBRef
    private BookEntity book;

    @DBRef
    private ClientEntity client;

    private LocalDate loanDate;
    private LocalDate returnDate;
    private boolean isReturned;

    // Constructors
    public LoanEntity() {}

    public LoanEntity(BookEntity book, ClientEntity client, LocalDate loanDate, LocalDate returnDate) {
        this.book = book;
        this.client = client;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
        this.isReturned = false;
    }

    public String getId() {
        return id;
    }

    public BookEntity getBook() {
        return book;
    }

    public ClientEntity getClient() {
        return client;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public boolean getIsReturned() {
        return isReturned;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBook(BookEntity book) {
        this.book = book;
    }

    public void setClient(ClientEntity client) {
        this.client = client;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public void  setIsReturned(boolean isReturned) {
        this.isReturned = isReturned;
    }

    @Override
    public String toString() {
        return "LoanEntity{" +
                "id='" + id + '\'' +
                ", book=" + (book != null ? book.toString() : "null") +
                ", client=" + (client != null ? client.toString() : "null") +
                ", loanDate=" + loanDate +
                ", returnDate=" + returnDate +
                ", isReturned=" + isReturned +
                '}';
    }
}