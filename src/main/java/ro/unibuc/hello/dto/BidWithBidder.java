package ro.unibuc.hello.dto;

import ro.unibuc.hello.data.BidEntity;

public class BidWithBidder {

    private String id;
    private int price;
    private User bidder;

    public BidWithBidder() {}

    public BidWithBidder(String id, int price, User bidder) {
        this.id = id;
        this.price = price;
        this.bidder = bidder;
    }

    public BidWithBidder(BidEntity entity) {
        this(
            entity.getId(),
            entity.getPrice(),
            new User(entity.getBidder())
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
}
