package ro.unibuc.hello.dto;

import java.time.LocalDateTime;

public class ItemPopularity {
    private String itemId;
    private String itemName;
    private int totalBids;
    private int uniqueBidders;
    private double bidFrequency; // bids per day
    private double bidIncreaseRate; // average percentage increase between bids
    private double popularityScore;
    private LocalDateTime lastBidTime;
    private int viewCount; // if you want to track views in the future
    private double priceIncrease; // percentage increase from initial price
    private boolean isHot; // flag for especially active items

    public ItemPopularity() {
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getTotalBids() {
        return totalBids;
    }

    public void setTotalBids(int totalBids) {
        this.totalBids = totalBids;
    }

    public int getUniqueBidders() {
        return uniqueBidders;
    }

    public void setUniqueBidders(int uniqueBidders) {
        this.uniqueBidders = uniqueBidders;
    }

    public double getBidFrequency() {
        return bidFrequency;
    }

    public void setBidFrequency(double bidFrequency) {
        this.bidFrequency = bidFrequency;
    }

    public double getBidIncreaseRate() {
        return bidIncreaseRate;
    }

    public void setBidIncreaseRate(double bidIncreaseRate) {
        this.bidIncreaseRate = bidIncreaseRate;
    }

    public double getPopularityScore() {
        return popularityScore;
    }

    public void setPopularityScore(double popularityScore) {
        this.popularityScore = popularityScore;
    }

    public LocalDateTime getLastBidTime() {
        return lastBidTime;
    }

    public void setLastBidTime(LocalDateTime lastBidTime) {
        this.lastBidTime = lastBidTime;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public double getPriceIncrease() {
        return priceIncrease;
    }

    public void setPriceIncrease(double priceIncrease) {
        this.priceIncrease = priceIncrease;
    }

    public boolean isHot() {
        return isHot;
    }

    public void setHot(boolean hot) {
        isHot = hot;
    }
}