package ro.unibuc.hello.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class AuctionStats {
    private int totalItems;
    private int activeItems;
    private int completedAuctions;
    private int totalBids;
    private double averageBids;
    private double highestBidAmount;
    private double averageBidAmount;
    private Map<String, Integer> itemsByCategory;

    // Popularity metrics
    private int uniqueBidders;
    private double bidsPerDay;
    private double bidsPerItem;
    private Map<String, Integer> bidsByDay;
    private Map<String, Double> categoryPopularity;
    private double popularityScore;
    private Map<String, Integer> topBidders;
    private Map<String, Integer> mostActiveTimes;

    private LocalDateTime generatedAt;

    public AuctionStats() {
        this.generatedAt = LocalDateTime.now();
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public int getActiveItems() {
        return activeItems;
    }

    public void setActiveItems(int activeItems) {
        this.activeItems = activeItems;
    }

    public int getCompletedAuctions() {
        return completedAuctions;
    }

    public void setCompletedAuctions(int completedAuctions) {
        this.completedAuctions = completedAuctions;
    }

    public int getTotalBids() {
        return totalBids;
    }

    public void setTotalBids(int totalBids) {
        this.totalBids = totalBids;
    }

    public double getAverageBids() {
        return averageBids;
    }

    public void setAverageBids(double averageBids) {
        this.averageBids = averageBids;
    }

    public double getHighestBidAmount() {
        return highestBidAmount;
    }

    public void setHighestBidAmount(double highestBidAmount) {
        this.highestBidAmount = highestBidAmount;
    }

    public double getAverageBidAmount() {
        return averageBidAmount;
    }

    public void setAverageBidAmount(double averageBidAmount) {
        this.averageBidAmount = averageBidAmount;
    }

    public Map<String, Integer> getItemsByCategory() {
        return itemsByCategory;
    }

    public void setItemsByCategory(Map<String, Integer> itemsByCategory) {
        this.itemsByCategory = itemsByCategory;
    }

    public int getUniqueBidders() {
        return uniqueBidders;
    }

    public void setUniqueBidders(int uniqueBidders) {
        this.uniqueBidders = uniqueBidders;
    }

    public double getBidsPerDay() {
        return bidsPerDay;
    }

    public void setBidsPerDay(double bidsPerDay) {
        this.bidsPerDay = bidsPerDay;
    }

    public double getBidsPerItem() {
        return bidsPerItem;
    }

    public void setBidsPerItem(double bidsPerItem) {
        this.bidsPerItem = bidsPerItem;
    }

    public Map<String, Integer> getBidsByDay() {
        return bidsByDay;
    }

    public void setBidsByDay(Map<String, Integer> bidsByDay) {
        this.bidsByDay = bidsByDay;
    }

    public Map<String, Double> getCategoryPopularity() {
        return categoryPopularity;
    }

    public void setCategoryPopularity(Map<String, Double> categoryPopularity) {
        this.categoryPopularity = categoryPopularity;
    }

    public double getPopularityScore() {
        return popularityScore;
    }

    public void setPopularityScore(double popularityScore) {
        this.popularityScore = popularityScore;
    }

    public Map<String, Integer> getTopBidders() {
        return topBidders;
    }

    public void setTopBidders(Map<String, Integer> topBidders) {
        this.topBidders = topBidders;
    }

    public Map<String, Integer> getMostActiveTimes() {
        return mostActiveTimes;
    }

    public void setMostActiveTimes(Map<String, Integer> mostActiveTimes) {
        this.mostActiveTimes = mostActiveTimes;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt = generatedAt;
    }
}