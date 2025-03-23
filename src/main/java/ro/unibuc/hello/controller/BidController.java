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

import ro.unibuc.hello.dto.Bid;
import ro.unibuc.hello.dto.BidPost;
import ro.unibuc.hello.dto.UserDetails;
import ro.unibuc.hello.dto.Auction;
import ro.unibuc.hello.dto.UserPost;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.BidService;

import java.util.List;



@Controller
public class BidController {

    @Autowired
    private BidService bidsService;

    @GetMapping("/bids")
    @ResponseBody
    public List<Bid> getAll() {
        return bidsService.getAllBids();
    }

    
    @PostMapping("/bids")
    @ResponseBody
    public Bid createBid(@RequestBody BidPost bid) {
        return bidsService.saveBid(bid);
    }

    @DeleteMapping("/bids/{id}")
    @ResponseBody
    public void deleteBid(@PathVariable String id) throws EntityNotFoundException {
          bidsService.deleteBid(id);
    }

    
    @GetMapping("/bids/{id}")
    public ResponseEntity<Bid> getBidById(@PathVariable String id) {
          
        Bid bid = bidsService.getBidById(id);

        if (bid != null) {
          return new ResponseEntity<>(bid, HttpStatus.OK);
        } else {
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/bids/{id}")
    @ResponseBody
    public Bid updateBid(@PathVariable String id, @RequestBody BidPost bid) throws EntityNotFoundException {
        return bidsService.updateBid(id, bid);
    }
}
