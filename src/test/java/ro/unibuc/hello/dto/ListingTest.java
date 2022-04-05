package ro.unibuc.hello.dto;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ListingTest {

    Product product = new Product("Yeezy 500","Tan",350);
    User user = new User("Alexandru", "Voiculescu");
    Listing listing = new Listing(user,650,product);

    @Test
    void getListingId(){
        listing.setListingId("33asd1lasd012sdmn0912");

        assertSame("33asd1lasd012sdmn0912", listing.getListingId());
    }

    @Test
    void setListingId(){
        listing.setListingId("123hg2j345gh534b6j45h");

        assertSame("123hg2j345gh534b6j45h", listing.getListingId());
    }

    @Test
    void getListingOwner() {

        assertSame(user, listing.getListingOwner());

    }

    @Test
    void setListingOwner() {

        User newUser = new User("Valentin","Paleolog");

        listing.setListingOwner(newUser);

        assertSame(newUser, listing.getListingOwner());
    }

    @Test
    void getStartingPrice() {

        assertEquals(Optional.of(650).get(), Optional.ofNullable(listing.getStartingPrice()).get());

    }

    @Test
    void setStartingPrice() {

        listing.setStartingPrice(700);

        assertEquals(Optional.of(700).get(), Optional.ofNullable(listing.getStartingPrice()).get());
    }

    @Test
    void getListedProduct() {
        assertSame(product,listing.getListedProduct());
    }

    @Test
    void setListedProduct() {
        Product newProduct = new Product("Air Jordan 1","Chicago",140);

        listing.setListedProduct(newProduct);

        assertSame(newProduct, listing.getListedProduct());
    }

    @Test
    void getCurrentPrice() {
        assertEquals(Optional.of(650).get(), Optional.ofNullable(listing.getCurrentPrice()).get());
    }

    @Test
    void setCurrentPrice() {

        listing.setCurrentPrice(700);

        assertEquals(Optional.of(700).get(), Optional.ofNullable(listing.getCurrentPrice()).get());
    }
}