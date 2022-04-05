package ro.unibuc.hello.service;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ro.unibuc.hello.data.ListingRepository;
import ro.unibuc.hello.dto.Listing;
import ro.unibuc.hello.dto.Product;
import ro.unibuc.hello.dto.User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class ListingServiceTest {

    @Mock
    private ListingRepository listingRepository;

    @Autowired
    ListingService listingService = new ListingService();



    @BeforeEach
    public void dataSetup(){
        Product product = new Product("Yeezy 500","Tan",350);
        User user = new User("Alexandru", "Voiculescu");
        Listing listing = new Listing(user,650,product);

        listing.setListingId("1kl3j1j2lk3");

        listingRepository.save(listing);
    }

    @Test
    void increaseListingPrice() {
        Product product = new Product("Yeezy 500","Tan",350);
        User user = new User("Alexandru", "Voiculescu");
        Listing listing = new Listing(user,650,product);

        listing.setListingId("1kl3j1j2lk3");
        listingRepository.save(listing);

        Integer value = 100;

        //when(listingRepository.findListingById("1kl3j1j2lk3")).thenReturn(listing);

        Listing newListing = listingService.increaseListingPrice(100,listing.getListingId());

        assertEquals(Optional.of(listing.getStartingPrice()+value).get(), Optional.ofNullable(newListing.getCurrentPrice()).get());

    }
}