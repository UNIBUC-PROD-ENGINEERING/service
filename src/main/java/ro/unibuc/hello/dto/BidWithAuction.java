package ro.unibuc.hello.dto;

import ro.unibuc.hello.data.BidEntity;

public class BidWithAuction {

    private String id;
    private int price;
    private Auction auction;

    public BidWithAuction() {}

    public BidWithAuction(String id, int price, Auction auction) {
        this.id = id;
        this.price = price;
        this.auction = auction;
    }

    public BidWithAuction(BidEntity entity) {
        this(
            entity.getId(),
            entity.getPrice(),
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

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }
}
