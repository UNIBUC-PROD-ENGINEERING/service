package ro.unibuc.hello.dto;

public class Bid{
    private int price;
    private String bidderName;
    private String auctionTitle;

    public Bid(int price, String bidderName, String auctionTitle) {
        this.price = price;
        this.bidderName = bidderName;
        this.auctionTitle = auctionTitle;
    }

    public int getPrice() {
        return price;
    }

    public String getBidder() {
        return bidderName;
    }

    public String getAuctionTitle(){
        return auctionTitle;
    }
}