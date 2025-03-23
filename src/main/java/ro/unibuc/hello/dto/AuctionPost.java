package ro.unibuc.hello.dto;

public class AuctionPost{

    private String title;
    private String description;
    private int startPrice;
    private String itemId;
    private String auctioneerId;

    public AuctionPost(String title, String description, int startPrice, String itemId, String auctioneerId) {
        this.title = title;
        this.description = description;
        this.startPrice = startPrice;
        this.itemId = itemId;
        this.auctioneerId = auctioneerId;
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

    public String getAuctioneerId() {
        return auctioneerId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public void setAuctioneerId(String auctioneerId) {
        this.auctioneerId = auctioneerId;
    }
}
