package ro.unibuc.hello.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    Product product = new Product("Yeezy 500","Tan",350);

    @Test
    void getProductId() {
        product.setProductId("33asd1lasd012sdmn0912");
        assertSame("33asd1lasd012sdmn0912", product.getProductId());
    }

    @Test
    void setProductId() {
        product.setProductId("asd219ncas9h21kbda9sxdh291h");
        assertSame("asd219ncas9h21kbda9sxdh291h", product.getProductId());
    }

    @Test
    void getModelName() {
        Assertions.assertSame("Yeezy 500", product.getModelName());
    }

    @Test
    void setModelName() {
        product.setModelName("Nike AirMax 95");
        Assertions.assertSame("Nike AirMax 95", product.getModelName());
    }

    @Test
    void getColorway() {
        Assertions.assertSame("Tan", product.getColorway());
    }

    @Test
    void setColorway() {
        product.setColorway("University Red");
        Assertions.assertSame("University Red", product.getColorway());
    }

    @Test
    void getRetailPrice() {
        Assertions.assertEquals(Optional.of(350).get(), Optional.ofNullable(product.getRetailPrice()).get());
    }

    @Test
    void setRetailPrice() {
        product.setRetailPrice(250);
        Assertions.assertEquals(Optional.of(250).get(), Optional.ofNullable(product.getRetailPrice()).get());
    }
}