package ro.unibuc.hello.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ro.unibuc.hello.data.MovieRepository;
import ro.unibuc.hello.dto.Location;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.LocationService;
import ro.unibuc.hello.data.LocationRepository;
import ro.unibuc.hello.data.LocationEntity;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
class LocationServiceTest {

    @Mock
    LocationRepository locationRepository;

    @InjectMocks
    LocationService locationService = new LocationService();

    @Test
    void test_getLocationByAddress_throwsEntityNotFoundException_whenAddressNotNull(){
        // Arrange
        String address = "someAddress";

        when(locationRepository.findByAddress(address)).thenReturn(null);

        try{
            // Act
            LocationEntity locationEntity = locationRepository.findByAddress(address);
        } catch (Exception ex) {
            // Assert
            Assertions.assertEquals(ex.getClass(), EntityNotFoundException.class);
            Assertions.assertEquals(ex.getMessage(), "Entity: someAddress was not found");
        }
    }

}
