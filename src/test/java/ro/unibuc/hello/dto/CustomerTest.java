package ro.unibuc.hello.dto;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import ro.unibuc.hello.data.MovieEntity;
import ro.unibuc.hello.data.TicketEntity;

import java.time.LocalDateTime;

public class CustomerTest {
    MovieEntity myMovie = new MovieEntity("Avatar",
            "Jake Sully lives with his newfound family formed on the extrasolar moon Pandora. Once a familiar threat returns to finish what was previously started, Jake must work with Neytiri and the army of the Na'vi race to protect their home.",
            192);
    TicketEntity newTicket = new TicketEntity(myMovie,29,6,2024,19,30);
    Customer myCustomer = new Customer("Andrei", 20, newTicket);

    @Test
    public void testAll() {
        Customer test = new Customer();
        test.setAge(20);
        test.setName("Andrei");
        test.setTicket(newTicket);
        Assertions.assertSame(myCustomer.getName(), test.getName());
        Assertions.assertSame(myCustomer.getAge(), test.getAge());
        Assertions.assertSame(newTicket.dateTime, test.getTicket().getDateTime());
        Assertions.assertSame(newTicket.movie.title, test.getTicket().getMovie().getTitle());
    }
}
