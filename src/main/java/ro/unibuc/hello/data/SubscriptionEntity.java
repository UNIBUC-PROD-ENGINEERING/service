package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;

public class SubscriptionEntity {

    @Id
    private String id;

    private int tier;

    private int price;

    public SubscriptionEntity() {}

    public SubscriptionEntity(int tier, int price) {
        this.tier = tier;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
