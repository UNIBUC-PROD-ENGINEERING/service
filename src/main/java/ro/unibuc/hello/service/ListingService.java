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
    ListingRepository listingRepository;

    public Listing increaseListingPrice(Integer value, String listingId){
        Listing listing = listingRepository.findListingById(listingId);
        Integer currPrice = listing.getCurrentPrice();
        Integer newValue = currPrice + value;
        listing.setCurrentPrice(newValue);
        System.out.println(listing.getCurrentPrice());
        listingRepository.save(listing);
        System.out.println(listing.getCurrentPrice());

        return listing;
    }
}
