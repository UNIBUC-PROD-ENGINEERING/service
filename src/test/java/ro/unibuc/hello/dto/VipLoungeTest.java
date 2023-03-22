package ro.unibuc.hello.dto;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import ro.unibuc.hello.data.LocationEntity;

public class VipLoungeTest {

    LocationEntity newLocationEntity = new LocationEntity("Drumul Taberei nr.5", "Multicinema", "0777777777");
    VipLounge myVipLounge = new VipLounge("350lei", newLocationEntity);

    @Test
    public void testAll() {
        VipLounge test = new VipLounge();
        test.setEntryPrice("350lei");
        test.setLocation(newLocationEntity);
        Assertions.assertSame(myVipLounge.getEntryPrice(), test.entryPrice);
        Assertions.assertSame(newLocationEntity.name, test.getLocation().name);
        Assertions.assertSame(newLocationEntity.address, test.getLocation().address);
        Assertions.assertSame(newLocationEntity.phoneNumber, test.getLocation().phoneNumber);
    }
}
