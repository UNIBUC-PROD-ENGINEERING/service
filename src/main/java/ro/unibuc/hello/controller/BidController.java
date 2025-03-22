package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.dto.Bid;
import ro.unibuc.hello.exception.BidException;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.BidService;

import java.util.List;

@RestController
@RequestMapping("/bids")
public class BidController {

    @Autowired
    private BidService bidService;

    @GetMapping
    public ResponseEntity<List<Bid>> getAllBids() {
        List<Bid> bids = bidService.getAllBids();
        return new ResponseEntity<>(bids, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bid> getBidById(@PathVariable String id) {
        try {
            Bid bid = bidService.getBidById(id);
            return new ResponseEntity<>(bid, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/item/{itemId}")
    public ResponseEntity<List<Bid>> getBidsByItem(@PathVariable String itemId) {
        List<Bid> bids = bidService.getBidsByItem(itemId);
        return new ResponseEntity<>(bids, HttpStatus.OK);
    }

    @GetMapping("/bidder/{bidderName}")
    public ResponseEntity<List<Bid>> getBidsByBidder(@PathVariable String bidderName) {
        List<Bid> bids = bidService.getBidsByBidder(bidderName);
        return new ResponseEntity<>(bids, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> placeBid(@RequestBody Bid bid) {
        try {
            Bid placedBid = bidService.placeBid(bid);
            return new ResponseEntity<>(placedBid, HttpStatus.CREATED);
        } catch (BidException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBid(@PathVariable String id) {
        try {
            bidService.deleteBid(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/bidder-email/{email}")
    public ResponseEntity<List<Bid>> getBidsByEmail(@PathVariable String email) {
        List<Bid> bids = bidService.getBidsByEmail(email);
        return new ResponseEntity<>(bids, HttpStatus.OK);
    }
}