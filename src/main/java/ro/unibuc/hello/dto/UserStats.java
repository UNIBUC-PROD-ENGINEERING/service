package ro.unibuc.hello.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class UserStats {
    private String email;
    private int totalItemsListed;
    private int activeItemsListed;
    private int completedAuctions;
    private int totalBidsMade;
    private int bidsMadeLastWeek;
    private int bidsWon;
    private double totalSpent;
    private double totalEarned;

    // Engagement metrics
    private double biddingFrequency; // bids per day
    private int uniqueItemsBidOn;
    private double averageBidAmount;
    private Map<String, Integer> bidsByCategory;
    private Map<String, Integer> biddingTimeDistribution; // hour of day distribution
    private double userActivityScore;
    private int bidStreak; // consecutive days with bids

    private LocalDateTime generatedAt;

    public UserStats() {
        this.generatedAt = LocalDateTime.now();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTotalItemsListed() {
        return totalItemsListed;
    }

    public void setTotalItemsListed(int totalItemsListed) {
        this.totalItemsListed = totalItemsListed;
    }

    public int getActiveItemsListed() {
        return activeItemsListed;
    }

    public void setActiveItemsListed(int activeItemsListed) {
        this.activeItemsListed = activeItemsListed;
    }

    public int getCompletedAuctions() {
        return completedAuctions;
    }

    public void setCompletedAuctions(int completedAuctions) {
        this.completedAuctions = completedAuctions;
    }

    public int getTotalBidsMade() {
        return totalBidsMade;
    }

    public void setTotalBidsMade(int totalBidsMade) {
        this.totalBidsMade = totalBidsMade;
    }

    public int getBidsMadeLastWeek() {
        return bidsMadeLastWeek;
    }

    public void setBidsMadeLastWeek(int bidsMadeLastWeek) {
        this.bidsMadeLastWeek = bidsMadeLastWeek;
    }

    public int getBidsWon() {
        return bidsWon;
    }

    public void setBidsWon(int bidsWon) {
        this.bidsWon = bidsWon;
    }

    public double getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(double totalSpent) {
        this.totalSpent = totalSpent;
    }

    public double getTotalEarned() {
        return totalEarned;
    }

    public void setTotalEarned(double totalEarned) {
        this.totalEarned = totalEarned;
    }

    public double getBiddingFrequency() {
        return biddingFrequency;
    }

    public void setBiddingFrequency(double biddingFrequency) {
        this.biddingFrequency = biddingFrequency;
    }

    public int getUniqueItemsBidOn() {
        return uniqueItemsBidOn;
    }

    public void setUniqueItemsBidOn(int uniqueItemsBidOn) {
        this.uniqueItemsBidOn = uniqueItemsBidOn;
    }

    public double getAverageBidAmount() {
        return averageBidAmount;
    }

    public void setAverageBidAmount(double averageBidAmount) {
        this.averageBidAmount = averageBidAmount;
    }

    public Map<String, Integer> getBidsByCategory() {
        return bidsByCategory;
    }

    public void setBidsByCategory(Map<String, Integer> bidsByCategory) {
        this.bidsByCategory = bidsByCategory;
    }

    public Map<String, Integer> getBiddingTimeDistribution() {
        return biddingTimeDistribution;
    }

    public void setBiddingTimeDistribution(Map<String, Integer> biddingTimeDistribution) {
        this.biddingTimeDistribution = biddingTimeDistribution;
    }

    public double getUserActivityScore() {
        return userActivityScore;
    }

    public void setUserActivityScore(double userActivityScore) {
        this.userActivityScore = userActivityScore;
    }

    public int getBidStreak() {
        return bidStreak;
    }

    public void setBidStreak(int bidStreak) {
        this.bidStreak = bidStreak;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt = generatedAt;
    }
}