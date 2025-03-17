package ro.unibuc.hello.dto;

public class Bid{
    private int price;
    private String bidderName;

    public Bid(int price, String bidderName) {
        this.price = price;
        this.bidderName = bidderName;
    }

    public int getPrice() {
        return price;
    }

    public String getBidder() {
        return bidderName;
    }
}