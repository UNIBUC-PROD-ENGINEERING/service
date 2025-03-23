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

import jakarta.servlet.http.HttpServletRequest;
import ro.unibuc.hello.auth.AuthUtil;
import ro.unibuc.hello.auth.PublicEndpoint;
import ro.unibuc.hello.dto.AuctionPlaceBidRequest;
import ro.unibuc.hello.dto.AuctionPost;
import ro.unibuc.hello.dto.AuctionWithAuctioneerAndItem;
import ro.unibuc.hello.dto.BidWithBidder;
import ro.unibuc.hello.permissions.AuctionPermissionChecker;
import ro.unibuc.hello.service.AuctionsService;


@Controller
public class AuctionsController {

    @Autowired
    private AuctionsService auctionsService;

    @Autowired
    private AuctionPermissionChecker permissionChecker;

    @PublicEndpoint
    @GetMapping("/auctions")
    @ResponseBody
    public List<AuctionWithAuctioneerAndItem> getAll() {
        return auctionsService.getAllAuctions();
    }

    @PublicEndpoint
    @GetMapping("/auctions/{id}")
    @ResponseBody
    public AuctionWithAuctioneerAndItem getAuctionById(@PathVariable String id) {
        return auctionsService.getAuctionById(id);
    }

    @PublicEndpoint
    @GetMapping("/auctions/{id}/highest-bid")
    @ResponseBody
    public BidWithBidder getAuctionHighestBid(@PathVariable String id) {
        return auctionsService.getAuctionHighestBid(id);
    }

    @PublicEndpoint
    @GetMapping("/auctions/{id}/bids")
    @ResponseBody
    public List<BidWithBidder> getAuctionBids(@PathVariable String id) {
        return auctionsService.getAuctionBids(id);
    }

    @PostMapping("/auctions")
    @ResponseBody
    public AuctionWithAuctioneerAndItem create(HttpServletRequest request, @RequestBody AuctionPost auction) {
        String userId = AuthUtil.getAuthenticatedUserId(request);
        return auctionsService.saveAuction(userId, auction);
    }

    @PutMapping("/auctions/{id}")
    @ResponseBody
    public AuctionWithAuctioneerAndItem updateAuction(HttpServletRequest request, @PathVariable String id, @RequestBody AuctionWithAuctioneerAndItem auction) {
        String userId = AuthUtil.getAuthenticatedUserId(request);
        permissionChecker.checkOwnership(userId, id);
        return auctionsService.updateAuction(id, auction);
    }

    @PostMapping("/auctions/{id}/place-bid")
    @ResponseBody
    public BidWithBidder placeBid(HttpServletRequest request, @PathVariable String id, @RequestBody AuctionPlaceBidRequest bid) {
        String userId = AuthUtil.getAuthenticatedUserId(request);
        return auctionsService.placeBid(id, userId, bid);
    }

    @DeleteMapping("/auctions/{id}")
    @ResponseBody
    public void deleteAuction(HttpServletRequest request, @PathVariable String id) {
        String userId = AuthUtil.getAuthenticatedUserId(request);
        permissionChecker.checkOwnership(userId, id);
        auctionsService.deleteAuction(id);
    }
}
