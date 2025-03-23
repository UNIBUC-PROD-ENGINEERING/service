package ro.unibuc.hello.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.unibuc.hello.auth.PublicEndpoint;
import ro.unibuc.hello.dto.Auction;
import ro.unibuc.hello.dto.AuctionDetails;
import ro.unibuc.hello.dto.AuctionPost;
import ro.unibuc.hello.service.AuctionService;


@Controller
public class AuctionController {

    @Autowired
    private AuctionService auctionsService;

    @PublicEndpoint
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

    @PublicEndpoint
    @GetMapping("/auctions/{id}")
    @ResponseBody
    public AuctionDetails getAuctionById(@PathVariable String id) {
        return auctionsService.getAuctionById(id);
    }

    @PutMapping("/auctions/{id}")
    @ResponseBody
    public Auction updateAuction(@PathVariable String id, @RequestBody Auction auction) {
        return auctionsService.updateAuction(id, auction);
    }

    @DeleteMapping("/auctions/{id}")
    @ResponseBody
    public void deleteAuction(@PathVariable String id) {
        auctionsService.deleteAuction(id);
    }
}
