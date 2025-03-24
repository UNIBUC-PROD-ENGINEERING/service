package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.dto.AuctionStats;
import ro.unibuc.hello.dto.ItemPopularity;
import ro.unibuc.hello.dto.UserStats;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.StatsService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stats")
public class StatsController {

    @Autowired
    private StatsService statsService;

    @GetMapping("/overview")
    public ResponseEntity<AuctionStats> getOverallStats() {
        AuctionStats stats = statsService.getOverallStats();
        return new ResponseEntity<>(stats, HttpStatus.OK);
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<UserStats> getUserStats(@PathVariable String email) {
        try {
            UserStats stats = statsService.getUserStats(email);
            return new ResponseEntity<>(stats, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/item/{itemId}")
    public ResponseEntity<ItemPopularity> getItemPopularity(@PathVariable String itemId) {
        try {
            ItemPopularity stats = statsService.getItemPopularity(itemId);
            return new ResponseEntity<>(stats, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<AuctionStats> getCategoryStats(@PathVariable String category) {
        try {
            AuctionStats stats = statsService.getCategoryStats(category);
            return new ResponseEntity<>(stats, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/popular-items")
    public ResponseEntity<List<ItemPopularity>> getPopularItems(
            @RequestParam(required = false, defaultValue = "10") int limit) {
        List<ItemPopularity> popularItems = statsService.getPopularItems(limit);
        return new ResponseEntity<>(popularItems, HttpStatus.OK);
    }

    @GetMapping("/popular-categories")
    public ResponseEntity<Map<String, Double>> getPopularCategories() {
        Map<String, Double> popularCategories = statsService.getPopularCategories();
        return new ResponseEntity<>(popularCategories, HttpStatus.OK);
    }

    @GetMapping("/active-users")
    public ResponseEntity<List<UserStats>> getMostActiveUsers(
            @RequestParam(required = false, defaultValue = "10") int limit) {
        List<UserStats> activeUsers = statsService.getMostActiveUsers(limit);
        return new ResponseEntity<>(activeUsers, HttpStatus.OK);
    }

    @GetMapping("/bidding-hours")
    public ResponseEntity<Map<String, Integer>> getBiddingHourDistribution() {
        Map<String, Integer> hourDistribution = statsService.getBiddingHourDistribution();
        return new ResponseEntity<>(hourDistribution, HttpStatus.OK);
    }

    @GetMapping("/hot-items")
    public ResponseEntity<List<ItemPopularity>> getHotItems() {
        List<ItemPopularity> hotItems = statsService.getHotItems();
        return new ResponseEntity<>(hotItems, HttpStatus.OK);
    }
}