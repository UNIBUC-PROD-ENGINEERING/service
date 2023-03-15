package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class VipLoungeEntity {

    @Id
    public String id;

    public String entryPrice;

    @DBRef
    public LocationEntity location;

    public VipLoungeEntity(String entryPrice, LocationEntity location) {
        this.location = location;
        this.entryPrice = entryPrice;
    }

    @Override
    public String toString() {
        return String.format(
                "VipLounge[entryPrice='%s', location='%s'", entryPrice, location
        );
    }
}
