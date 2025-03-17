package ro.unibuc.hello.data;

import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.springframework.data.annotation.Id;

@Document
public class AuctionEntity {

    @Id
    private String id;
    private String title;
    private String description;
    private int startPrice;

    @JsonManagedReference
    private ItemEntity item;
    @JsonManagedReference
    private BidEntity highestBid;
    @JsonManagedReference
    private UserEntity auctioneer;

    public AuctionEntity(){}

    public AuctionEntity(String title, String description, ItemEntity item, UserEntity auctioneer, int startPrice, BidEntity highestBid) {
        this.title = title;
        this.description = description;
        this.startPrice = startPrice;
        this.item = item;
        this.auctioneer = auctioneer;
        this.highestBid = highestBid;
    }

    
    public AuctionEntity(String title, String description, int startPrice) {
        this.title = title;
        this.description = description;
        this.startPrice = startPrice;
    }

    public AuctionEntity(String Id,String title, String description, ItemEntity item, UserEntity auctioneer, int startPrice, BidEntity highestBid) {
        this.id = Id;
        this.title = title;
        this.description = description;
        this.item = item;
        this.auctioneer = auctioneer;
        this.startPrice = startPrice;
        this.highestBid = highestBid;
    }

    public UserEntity getAuctioneer() {
        return auctioneer;
    }

    public void setAuctioneer(UserEntity auctioneer) {
        this.auctioneer = auctioneer;
    }

    
    public int getStartPrice() {
        return startPrice;
    }

    public String getId() {
        return id;
    }


    public void setId(String Id) {
        this.id = Id;
    }

    public void setStartPrice(int startPrice) {
        this.startPrice = startPrice;
    }

    public BidEntity getHighestBid() {
        return highestBid;
    }

    public void setHighestBid(BidEntity highestBid) {
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

    public ItemEntity getItem() {
        return item;
    }

    public void setItem(ItemEntity item) {
        this.item = item;
    }


    @Override
    public String toString() {
        int highestBidPrice = (highestBid != null) ? highestBid.getPrice() : 0;

        return "Auction {" +
                "Title='" + title + '\'' +
                ", Description='" + description + '\'' +
                ", ItemEntity=" +  getItem().getName() + '\'' +
                ", UserEntity=" + getAuctioneer().getName() + '\'' +
                ", Start Price=" + startPrice +
                ", Highest BidEntity=" + highestBidPrice +
                '}';
    }
}