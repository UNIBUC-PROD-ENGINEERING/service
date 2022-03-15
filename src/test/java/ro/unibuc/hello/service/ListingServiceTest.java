package ro.unibuc.hello.service;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import ro.unibuc.hello.data.ListingRepository;
import ro.unibuc.hello.dto.Listing;
import ro.unibuc.hello.dto.Product;
import ro.unibuc.hello.dto.User;

class ListingServiceTest {

    /*@Autowired
    ListingService listingService;

    @Before
    public void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void increaseListingPrice() {
        Product product = new Product("Yeezy 500","Tan",350);
        User user = new User("Alexandru", "Voiculescu");
        Listing listing = new Listing(user,650,product);

        listing.setListingId("jk214n23kj5n34k53k4j");

        listingService.increaseListingPrice(100,listing.getListingId());

        Assertions.assertEquals(750,listing.getCurrentPrice());

    }*/
}