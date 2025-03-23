package ro.unibuc.hello.dto;

import java.util.ArrayList;
import java.util.List;

import ro.unibuc.hello.data.AuctionEntity;

public class AuctionDetails {

    private String id;
    private String title;
    private String description;
    private int startPrice;
    private User auctioneer;
    private Item item;
    private Bid highestBid;
    private List<Bid> bids = new ArrayList<>();

    public AuctionDetails() {}

    public AuctionDetails(String id, String title, String description, int startPrice, User auctioneer, Item item, Bid highestBid, List<Bid> bids) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startPrice = startPrice;
        this.auctioneer = auctioneer;
        this.item = item;
        this.highestBid = highestBid;
        this.bids = bids;
    }

    public AuctionDetails(AuctionEntity auctionEntity, Bid highestBid, List<Bid> bids) {
        this(
            auctionEntity.getId(),
            auctionEntity.getTitle(),
            auctionEntity.getDescription(),
            auctionEntity.getStartPrice(),
            new User(auctionEntity.getAuctioneer()),
            new Item(auctionEntity.getItem()),
            highestBid,
            bids
        );
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(int startPrice) {
        this.startPrice = startPrice;
    }

    public User getAuctioneer() {
        return auctioneer;
    }

    public void setAuctioneer(User auctioneer) {
        this.auctioneer = auctioneer;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Bid getHighestBid() {
        return highestBid;
    }

    public void setHighestBid(Bid highestBid) {
        this.highestBid = highestBid;
    }

    public List<Bid> getPlacedBids() {
        return bids;
    }

    public void addBid(Bid bid) {
        this.bids.add(bid);
    }

    public void eraseItem(Bid bid) {
        this.bids.remove(bid);
    }
}
