package ro.unibuc.hello.dto;

public class AuctionPlaceBidRequest {

    private int price;

    public AuctionPlaceBidRequest() {}

    public AuctionPlaceBidRequest(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
