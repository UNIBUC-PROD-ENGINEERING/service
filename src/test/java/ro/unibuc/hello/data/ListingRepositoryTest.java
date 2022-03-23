package ro.unibuc.hello.data;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ro.unibuc.hello.dto.Listing;
import ro.unibuc.hello.dto.Product;
import ro.unibuc.hello.dto.User;
import ro.unibuc.hello.service.ListingService;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class ListingRepositoryTest {

    @Autowired
    ListingRepository listingRepository;

    @BeforeEach
    public void dataSetup(){
        Product product = new Product("Yeezy 500","Tan",350);
        User user = new User("Alexandru", "Voiculescu");
        Listing listing = new Listing(user,650,product);

        listing.setListingId("1kl3j1j2lk3");

        listingRepository.save(listing);
    }


    @Test
    void findListingById(){
        Product product = new Product("Yeezy 500","Tan",350);
        User user = new User("Alexandru", "Voiculescu");
        Listing listing = new Listing(user,650,product);

        listing.setListingId("1kl3j1j2lk3");

        Listing result = listingRepository.findListingById("1kl3j1j2lk3");

        assertEquals(listing.getStartingPrice(),result.getStartingPrice());
        assertEquals(listing.getListingId(),result.getListingId());
        assertEquals(listing.getCurrentPrice(),result.getCurrentPrice());
    }


}