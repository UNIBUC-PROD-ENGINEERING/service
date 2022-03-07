package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.unibuc.hello.data.ListingRepository;
import ro.unibuc.hello.dto.Listing;

import java.util.List;

@Controller
public class ListingController {

    @Autowired
    private ListingRepository listingRepository;

    @PostMapping("/post_listing")
    @ResponseBody
    public Listing postListing(@RequestBody Listing listing){
        listingRepository.save(listing);
        return listing;
    }


    @GetMapping("/listings")
    @ResponseBody
    public List<Listing> getAllListings(){
        return listingRepository.findAll();
    }
}
