package ro.unibuc.hello.dto;

public class AuctionPost{

    private String title;
    private String description;
    private int startPrice;
    private String itemId;

    public AuctionPost(String title, String description, int startPrice, String itemId) {
        this.title = title;
        this.description = description;
        this.startPrice = startPrice;
        this.itemId = itemId;
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

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
}
