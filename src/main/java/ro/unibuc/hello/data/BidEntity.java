package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Document
public class BidEntity {

    @Id
    private String id;
    private int price;

    @DocumentReference
    private UserEntity bidder;

    @DocumentReference
    private AuctionEntity auction;

    public BidEntity(){}

    public BidEntity(int price, UserEntity bidder, AuctionEntity auction) {
        this.price = price;
        this.bidder = bidder;
        this.auction = auction;
    }

    public BidEntity(String Id, int price, UserEntity bidder, AuctionEntity auction) {
        this.id = Id;
        this.price = price;
        this.bidder = bidder;
        this.auction = auction;
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

    public UserEntity getBidder() {
        return bidder;
    }

    public void setBidder(UserEntity bidder) {
        this.bidder = bidder;
    }
    
    public AuctionEntity getAuction() {
        return auction;
    }

    public void setAuction(AuctionEntity auction) {
        this.auction = auction;
    }
}
