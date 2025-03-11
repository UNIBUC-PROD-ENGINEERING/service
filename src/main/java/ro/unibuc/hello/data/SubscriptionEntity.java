package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;

public class SubscriptionEntity {

    @Id
    private int tier;

    private int price;

    public SubscriptionEntity() {}

    public SubscriptionEntity(int price) {
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

    @Override
    public String toString() {
        return String.format(
                "Subscription[tier='%d', price='%d']",
                tier, price);
    }
}
