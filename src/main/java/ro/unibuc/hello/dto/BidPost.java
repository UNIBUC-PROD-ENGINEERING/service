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

    public String getBidderId() {
        return bidderId;
    }

    public String getAuctionId() {
        return auctionId;
    }


}

