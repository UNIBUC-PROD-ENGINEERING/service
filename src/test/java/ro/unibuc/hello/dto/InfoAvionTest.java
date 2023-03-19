package ro.unibuc.hello.dto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
public class InfoAvionTest {

    InfoAvion infoAvion = new InfoAvion("Doha -> Bangkok");
    InfoAvion infoAvion_ParamlessConstructor = new InfoAvion();

    @Test
    void test_flight(){
        Assertions.assertSame("Doha -> Bangkok", infoAvion.getFlight());
    }
    @Test
    void test_setFlight(){
        infoAvion.setFlight("Doha -> Male");
        Assertions.assertSame("Doha -> Male", infoAvion.getFlight());
    }

    @Test
    void test_flight_paramless(){
        Assertions.assertSame(null, infoAvion_ParamlessConstructor.getFlight());
    }
    @Test
    void test_setFlight_paramless(){
        infoAvion_ParamlessConstructor.setFlight("London -> Buenos Aires");
        Assertions.assertSame("London -> Buenos Aires", infoAvion_ParamlessConstructor.getFlight());
    }
}
