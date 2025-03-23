package ro.unibuc.hello.dto;

public class BidPost {

    private int price;
    private String auctionId;

    public BidPost(int price, String auctionId) {
        this.price = price;
        this.auctionId = auctionId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(String auctionId) {
        this.auctionId = auctionId;
    }
}
