package ro.unibuc.hello.dto;

import ro.unibuc.hello.data.LocationEntity;

public class VipLounge {

    String entryPrice;

    Location location;

    public VipLounge(String entryPrice, LocationEntity locationEntity) {
        Location location1 = new Location(locationEntity.address, locationEntity.name, locationEntity.phoneNumber);
        location = location1;

        this.entryPrice = entryPrice;
    }

    public String getEntryPrice() {
        return entryPrice;
    }

    public void setEntryPrice(String entryPrice) {
        this.entryPrice = entryPrice;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
