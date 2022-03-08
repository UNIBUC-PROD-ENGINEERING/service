package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.data.ListingRepository;
import ro.unibuc.hello.dto.Listing;

import java.util.List;
import java.util.Optional;

@Service
public class ListingService {

    @Autowired
    Listing listing;
    @Autowired
    ListingRepository listingRepository;

    public void increaseListingPrice(Integer value, String listingId){
        Listing listing = listingRepository.findListingById(listingId);
        Integer currPrice = listing.getCurrentPrice();
        listing.setCurrentPrice(currPrice + value);
        listingRepository.save(listing);
    }
}
