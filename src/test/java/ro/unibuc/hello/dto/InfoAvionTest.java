package ro.unibuc.hello.dto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
public class InfoAvionTest {

    InfoAvion infoAvion = new InfoAvion("Doha -> Bangkok");

    @Test
    void test_flight(){
        Assertions.assertSame("Doha -> Bangkok", infoAvion.getFlight());
    }
    @Test
    void test_setFlight(){
        infoAvion.setFlight("Doha -> Male");
        Assertions.assertSame("Doha -> Male", infoAvion.getFlight());
    }
}
