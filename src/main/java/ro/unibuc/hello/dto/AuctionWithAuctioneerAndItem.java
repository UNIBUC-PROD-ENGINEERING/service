package ro.unibuc.hello.dto;

import ro.unibuc.hello.data.AuctionEntity;

public class AuctionWithAuctioneerAndItem {

    private String id;
    private String title;
    private String description;
    private int startPrice;
    private String status;
    private User auctioneer;
    private Item item;

    public AuctionWithAuctioneerAndItem() {}

    public AuctionWithAuctioneerAndItem(String id, String title, String description, int startPrice, String status, User auctioneer, Item item) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startPrice = startPrice;
        this.status = status;
        this.auctioneer = auctioneer;
        this.item = item;
    }

    public AuctionWithAuctioneerAndItem(AuctionEntity entity) {
        this(
            entity.getId(),
            entity.getTitle(),
            entity.getDescription(),
            entity.getStartPrice(),
            entity.isOpen() ? "open" : "closed",
            new User(entity.getAuctioneer()),
            new Item(entity.getItem())
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
