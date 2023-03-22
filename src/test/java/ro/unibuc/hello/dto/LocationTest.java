package ro.unibuc.hello.dto;

import io.cucumber.java.bs.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
public class LocationTest {

    Location location = new Location("Bulevardul Dacia, București 020051", "Cinema Elvira Popescu", "0374 125 216");

    @Test
    public void test_getAddress(){
        Assertions.assertSame("Bulevardul Dacia, București 020051", location.getAddress());
    }

    @Test
    public void test_getName(){
        Assertions.assertSame("Cinema Elvira Popescu", location.getName());
    }

    @Test
    public void test_getPhoneNumber(){
        Assertions.assertSame("0374 125 216", location.getPhoneNumber());
    }

    @Test
    public void test_setAddress(){
        location.setAddress("Bd Timisoara");
        Assertions.assertSame("Bd Timisoara", location.getAddress());
    }

    @Test
    public void test_setName(){
        location.setName("Cinemagia");
        Assertions.assertSame("Cinemagia", location.getName());
    }

    @Test
    public void test_setPhoneNumber(){
        location.setPhoneNumber("032 323 2321");
        Assertions.assertSame("032 323 2321", location.getPhoneNumber());
    }

    @Test
    public void test_ConstructorWithoutParams(){
        Location location = new Location();
        Assertions.assertSame(null, location.getAddress());
    }
}
