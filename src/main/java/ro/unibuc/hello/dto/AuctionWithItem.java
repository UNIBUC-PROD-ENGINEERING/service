package ro.unibuc.hello.dto;

import ro.unibuc.hello.data.AuctionEntity;

public class AuctionWithItem {

    private String id;
    private String title;
    private String description;
    private int startPrice;
    private Item item;
    private String status;

    public AuctionWithItem() {}

    public AuctionWithItem(String id, String title, String description, int startPrice, String status, Item item) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startPrice = startPrice;
        this.status = status;
        this.item = item;
    }

    public AuctionWithItem(AuctionEntity entity) {
        this(
            entity.getId(),
            entity.getTitle(),
            entity.getDescription(),
            entity.getStartPrice(),
            entity.isOpen() ? "open" : "closed",
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
