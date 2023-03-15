package ro.unibuc.hello.dto;

public class InfoAvion {

    private String flight;

    public InfoAvion() {
    }

    public InfoAvion(String flight) {
        this.flight = flight;
    }

    public void setFlight(String flight) {
        this.flight = flight;
    }


    public String getFlight() {
        return flight;
    }

}