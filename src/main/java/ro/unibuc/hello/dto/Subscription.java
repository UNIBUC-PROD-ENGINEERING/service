package ro.unibuc.hello.dto;

public class Subscription {
    private int tier;

    private int price;

    public Subscription() {}

    public Subscription(int tier, int price) {
        this.tier = tier;
        this.price = price;
    }

    public int getTier(){
        return tier;
    }

    public void setTier(int tier){
        this.tier = tier;
    }

    public int getPrice(){
        return price;
    }

    public void setPrice(int price){
        this.price = price;
    }
}