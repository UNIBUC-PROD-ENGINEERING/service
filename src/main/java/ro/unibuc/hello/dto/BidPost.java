package ro.unibuc.hello.dto;

public class BidPost {
   
    private int price;
    private String auctionId;
    private String bidderUsername;

    public BidPost(int price, String auctionId, String bidderUsername) {
        this.price = price;
        this.auctionId = auctionId;
        this.bidderUsername = bidderUsername;
    }

    public int getPrice() {
        return price;
    }

    public String getBidderUsername() {
        return bidderUsername;
    }

    public String getAuctionId() {
        return auctionId;
    }


}

