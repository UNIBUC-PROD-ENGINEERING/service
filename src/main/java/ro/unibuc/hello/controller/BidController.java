package ro.unibuc.hello.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import ro.unibuc.hello.auth.AuthUtil;
import ro.unibuc.hello.auth.PublicEndpoint;
import ro.unibuc.hello.dto.Bid;
import ro.unibuc.hello.dto.BidPost;
import ro.unibuc.hello.service.BidService;

@Controller
public class BidController {

    @Autowired
    private BidService bidsService;

    @PublicEndpoint
    @GetMapping("/bids")
    @ResponseBody
    public List<Bid> getAll() {
        return bidsService.getAllBids();
    }

    @PublicEndpoint
    @GetMapping("/bids/{id}")
    @ResponseBody
    public Bid getBidById(@PathVariable String id) {
        return bidsService.getBidById(id);
    }

    @PostMapping("/bids")
    @ResponseBody
    public Bid createBid(HttpServletRequest request, @RequestBody BidPost bid) {
        String userId = AuthUtil.getAuthenticatedUserId(request);
        return bidsService.saveBid(userId, bid);
    }
}
