package ro.unibuc.hello.dto;

public class BidPost {

    private int price;
    private String auctionId;
    private String bidderId;

    public BidPost(int price, String auctionId, String bidderId) {
        this.price = price;
        this.auctionId = auctionId;
        this.bidderId = bidderId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getBidderId() {
        return bidderId;
    }

    public void setBidderId(String bidderId) {
        this.bidderId = bidderId;
    }

    public String getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(String auctionId) {
        this.auctionId = auctionId;
    }
}
