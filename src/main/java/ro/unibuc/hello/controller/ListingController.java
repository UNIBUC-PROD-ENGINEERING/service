package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.unibuc.hello.data.ListingRepository;
import ro.unibuc.hello.data.ProductRepository;
import ro.unibuc.hello.dto.Listing;
import ro.unibuc.hello.dto.Product;

import java.util.List;

@Controller
public class ListingController {

    @Autowired
    private ListingRepository listingRepository;
    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/post_listing")
    @ResponseBody
    public Listing postListing(@RequestBody Listing listing){
        listingRepository.save(listing);
        return listing;
    }

    @PostMapping("/bid_for_listing")
    @ResponseBody
    public Listing bidForListing(@RequestBody Listing listing, @RequestBody int price){
        if(price <= listing.currentPrice)
            System.out.println("Invalid price");
        else
            listing.setCurrentPrice(price);
        listingRepository.save(listing);
        return listing;
    }

    @PostMapping("/register_product")
    @ResponseBody
    public Product registerProduct(@RequestBody Product product){
        productRepository.save(product);
        return product;
    }

    @GetMapping("/products")
    @ResponseBody
    public List<Product> getAllProducts(){return productRepository.findAll();}

    @GetMapping("/listings")
    @ResponseBody
    public List<Listing> getAllListings(){
        return listingRepository.findAll();
    }
}
