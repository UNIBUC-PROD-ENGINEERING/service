package ro.unibuc.hello.dto;

import ro.unibuc.hello.data.BidEntity;
import ro.unibuc.hello.data.ItemEntity;
import ro.unibuc.hello.data.UserEntity;

public class Auction{

    private String title;
    private String description;
    private int startPrice;
    
    private Item item;
    private String auctioneer;
    private Bid highestBid;



    public Auction(String title, String description, int startPrice, ItemEntity item, BidEntity highestBid, String auctioneer) {
        this.title = title;
        this.description = description;
        this.startPrice = startPrice;
        this.auctioneer = auctioneer;

        this.highestBid = new Bid(highestBid.getPrice(), item.getOwner().getName());
        this.item = new Item(item.getName(), item.getDescription(), item.getOwner().getName());
    }

    public String getAuctioneer() {
        return auctioneer;
    }

    public void setAuctioneer(String auctioneer) {
        this.auctioneer = auctioneer;
    }

    
    public int getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(int startPrice) {
        this.startPrice = startPrice;
    }

    public Bid getHighestBid() {
        return highestBid;
    }

    public void setHighestBid(Bid highestBid) {
        this.highestBid = highestBid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}