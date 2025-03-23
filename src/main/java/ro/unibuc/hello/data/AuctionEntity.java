package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Document
public class AuctionEntity {

    @Id
    private String id;
    private String title;
    private String description;
    private int startPrice;

    @DocumentReference
    private ItemEntity item;

    @DocumentReference
    private UserEntity auctioneer;

    private boolean open;

    public AuctionEntity() {}

    public AuctionEntity(String title, String description, int startPrice, ItemEntity item, UserEntity auctioneer, boolean open) {
        this.title = title;
        this.description = description;
        this.startPrice = startPrice;
        this.item = item;
        this.auctioneer = auctioneer;
        this.open = open;
    }

    public AuctionEntity(String id,String title, String description, int startPrice, ItemEntity item, UserEntity auctioneer, boolean open) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startPrice = startPrice;
        this.item = item;
        this.auctioneer = auctioneer;
        this.open = open;
    }

    public String getId() {
        return id;
    }

    public void setId(String Id) {
        this.id = Id;
    }

    public int getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(int startPrice) {
        this.startPrice = startPrice;
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

    public UserEntity getAuctioneer() {
        return auctioneer;
    }

    public void setAuctioneer(UserEntity auctioneer) {
        this.auctioneer = auctioneer;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    @Override
    public String toString() {
        return "Auction {" +
                "Title='" + title + '\'' +
                ", Description='" + description + '\'' +
                ", ItemEntity=" +  getItem().getName() + '\'' +
                ", UserEntity=" + getAuctioneer().getName() + '\'' +
                ", Start Price=" + startPrice +
                '}';
    }
}