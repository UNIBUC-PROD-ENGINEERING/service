package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import ro.unibuc.hello.dto.Movie;

import java.time.LocalDateTime;
import java.util.Calendar;

public class TicketEntity {
    @Id
    public String id;

    public LocalDateTime dateTime;

    @DBRef
    public MovieEntity movie;

    public TicketEntity(MovieEntity movie, Integer day, Integer month, Integer year, Integer hour, Integer minute){
        this.movie = movie;
        dateTime = LocalDateTime.of(year, month, day, hour,minute);
    }
    @Override
    public String toString() {
        return String.format(
                "Ticket[movie='%s']",
                movie);
    }
}
