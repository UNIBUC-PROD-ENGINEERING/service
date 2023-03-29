package ro.unibuc.hello.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ro.unibuc.hello.data.LocationRepository;
import ro.unibuc.hello.dto.Location;
import ro.unibuc.hello.dto.Greeting;

@SpringBootTest
@Tag("IT")
class LocationServiceTestIT {

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    LocationService locationService;

    @Test
    void test_findByAddress_returnPhoneNumber(){
        // Arrange
        String address = "Calea Moșilor 127, București 020854";
        String phoneNumber = "021 367 2567";

        // Act
        Location location = locationService.getLocationByAddress(address);

        // Assert
        Assertions.assertSame(phoneNumber, location.getPhoneNumber());

    }


}
