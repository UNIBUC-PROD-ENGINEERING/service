package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "Donations")

public class Donation {
        @Id
        private String id;
        private String sender;
        private Integer amount;
        private boolean anonymity;
public Donation(){

        }
            public Donation(String sender,Integer amount,boolean anonimity)
            {
                this.sender=sender;
                this.amount=amount;
                this.anonymity=anonimity;


            }
    public String getId() {
        return id;
    }
    public String getSender() {
        return sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }
    public Integer getAmount() {
        return amount;
    }
    public void setAmount(Integer amount) {
        this.amount = amount;
    }
    public boolean isAnonymous() {
        return anonymity;
    }
    public void setAnonymity(boolean isAnonymous) {
        this.anonymity= isAnonymous;
    }
    @Override
    public String toString() {
        return "Donation [id=" + id + ", sender=" + sender + ", amount=" + amount + ", anonymity=" + anonymity + "]";
    }

}
