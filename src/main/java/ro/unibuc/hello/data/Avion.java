package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;

public class Avion {

    @Id
    public String id;
    public String number;
    public String from;
    public String to;

    public Avion() {}

    public Avion(String number, String from, String to) {
        this.number = number;
        this.from = from;
        this.to = to;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getNumber() {
        return number;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }


    @Override
    public String toString() {
        return String.format(
                "Avion[number='%s', from='%s', to='%s']",
                id, number, from, to);
    }

}