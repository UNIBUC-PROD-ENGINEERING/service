package ro.unibuc.hello.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ro.unibuc.hello.data.LocationEntity;
public class CinemaRoomTest {
    LocationEntity newLocationEntity = new LocationEntity("Drumul Taberei nr.5", "Multicinema", "0777777777");

    CinemaRoom cinemaRoom = new CinemaRoom(newLocationEntity, 10);

    @Test
    public void testAll(){
        CinemaRoom test = new CinemaRoom();
        test.setNumber(10);
        test.setLocation(newLocationEntity);
        Assertions.assertSame(cinemaRoom.getNumber(), test.number);
        Assertions.assertSame(newLocationEntity.name, test.getLocation().getName());
        Assertions.assertSame(newLocationEntity.address, test.getLocation().getAddress());
        Assertions.assertSame(newLocationEntity.phoneNumber, test.getLocation().getPhoneNumber());
    }
}
