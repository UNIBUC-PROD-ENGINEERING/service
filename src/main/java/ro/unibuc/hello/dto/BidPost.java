package ro.unibuc.hello.dto;

public class BidPost {
   
    private int price;
    private String bidderId;

    public BidPost(int price, String bidderId) {
        this.price = price;
        this.bidderId = bidderId;
    }

    public int getPrice() {
        return price;
    }


    public String getBidderId() {
        return bidderId;
    }

}

