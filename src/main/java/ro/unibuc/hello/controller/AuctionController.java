package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.unibuc.hello.dto.Auction;
import ro.unibuc.hello.dto.AuctionPost;
import ro.unibuc.hello.dto.Greeting;
import ro.unibuc.hello.dto.UserDetails;
import ro.unibuc.hello.dto.UserPost;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.AuctionService;

import java.util.List;



@Controller
public class AuctionController {

    @Autowired
    private AuctionService auctionsService;

    @GetMapping("/auctions")
    @ResponseBody
    public List<Auction> getAll() {
        return auctionsService.getAllAuctions();
    }

    
    @PostMapping("/auctions")
    @ResponseBody
    public Auction create(@RequestBody AuctionPost auction) {
        return auctionsService.saveAuction(auction);
    }

   
    @PutMapping("/auctions/{id}")
    @ResponseBody
    public Auction updateAuction(@PathVariable String id, @RequestBody Auction auction) throws EntityNotFoundException {
        return auctionsService.updateAuction(id, auction);
    }

    @DeleteMapping("/auctions/{id}")
    @ResponseBody
    public void deleteAuction(@PathVariable String id) throws EntityNotFoundException {
          auctionsService.deleteAuction(id);
    }

    
    @GetMapping("/auctions/{id}")
    public ResponseEntity<Auction> getAuctionById(@PathVariable String id) {
          
        Auction auction = auctionsService.getAuctionById(id);

        if (auction != null) {
          return new ResponseEntity<>(auction, HttpStatus.OK);
        } else {
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}