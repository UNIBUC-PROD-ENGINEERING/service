package ro.unibuc.hello.data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    Address address = new Address(
            "Romania",
            "Timisoara",
            "10076"
    );

    @Test
    void getCountry() {
        Assertions.assertSame("Romania", address.getCountry());
    }

    @Test
    void setCountry() {
        address.setCountry("Uzbekistan");
        Assertions.assertSame("Uzbekistan", address.getCountry());
    }

    @Test
    void getCity() {
        Assertions.assertSame("Timisoara", address.getCity());
    }

    @Test
    void setCity() {
        address.setCity("Ploiesti");
        Assertions.assertSame("Ploiesti", address.getCity());
    }

    @Test
    void getPostCode() {
        Assertions.assertSame("10076", address.getPostCode());
    }

    @Test
    void setPostCode() {
        address.setPostCode("1012");
        Assertions.assertSame("1012", address.getPostCode());
    }
}