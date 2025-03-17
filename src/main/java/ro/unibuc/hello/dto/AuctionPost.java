package ro.unibuc.hello.dto;

public class AuctionPost{

    private String title;
    private String description;
    private int startPrice;
    
    private String itemId;
    private String auctioneerUsername;
    private String highestBidId;

    public AuctionPost(String title, String description, int startPrice, String itemId, String highestBidId, String auctioneer) {
        this.title = title;
        this.description = description;
        this.startPrice = startPrice;
        this.auctioneerUsername = auctioneer;
        this.highestBidId = highestBidId;
        this.itemId = itemId;
    }

    public String getAuctioneerUsername() {
        return auctioneerUsername;
    }
   
    public int getStartPrice() {
        return startPrice;
    }

    public String getHighestBid() {
        return highestBidId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getItem() {
        return itemId;
    }

}