package ro.unibuc.hello.dto;

import ro.unibuc.hello.data.BidEntity;

public class Bid {

    private String id;
    private int price;
    private User bidder;
    private Auction auction;

    public Bid() {}

    public Bid(String id, int price, User bidder, Auction auction) {
        this.id = id;
        this.price = price;
        this.bidder = bidder;
        this.auction = auction;
    }

    public Bid(BidEntity entity) {
        this(
            entity.getId(),
            entity.getPrice(),
            new User(entity.getBidder()),
            new Auction(entity.getAuction())
        );
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public User getBidder() {
        return bidder;
    }

    public void setBidder(User bidder) {
        this.bidder = bidder;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }
}
