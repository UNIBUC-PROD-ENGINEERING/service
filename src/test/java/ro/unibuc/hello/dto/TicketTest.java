package ro.unibuc.hello.dto;

import io.cucumber.java.bs.A;
import org.bson.codecs.jsr310.LocalDateTimeCodec;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import ro.unibuc.hello.data.MovieEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

public class TicketTest {
    MovieEntity myMovie = new MovieEntity("Avatar",
            "Jake Sully lives with his newfound family formed on the extrasolar moon Pandora. Once a familiar threat returns to finish what was previously started, Jake must work with Neytiri and the army of the Na'vi race to protect their home.",
            192);
    LocalDateTime myDateTime = LocalDateTime.of(2023,
            6, 29, 19, 30, 40);

    Ticket newTicket = new Ticket(myMovie, myDateTime);

    @Test
    public void test_Constructor() {
        Ticket objTest = new Ticket(myMovie, myDateTime);
        String result = "Avatar";
        Assertions.assertSame(myMovie.title, objTest.getMovie().getTitle());
        Assertions.assertSame(myMovie.description, objTest.getMovie().getDescription());
        Assertions.assertSame(myMovie.runtime, objTest.getMovie().getRuntime());
    }

    @Test
    public void test_getDateTime() {
        Assertions.assertSame(myDateTime.getDayOfMonth(), newTicket.getDateTime().getDayOfMonth());
        Assertions.assertSame(myDateTime.getMonth(), newTicket.getDateTime().getMonth());
    }
}
