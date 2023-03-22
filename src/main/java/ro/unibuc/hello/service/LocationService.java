package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.unibuc.hello.data.LocationEntity;
import ro.unibuc.hello.data.LocationRepository;
import ro.unibuc.hello.dto.Location;
import ro.unibuc.hello.exception.EntityNotFoundException;
@Component
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public Location getLocationByAddress(String address) throws EntityNotFoundException {
        LocationEntity entity = locationRepository.findByAddress(address);
        if(entity == null){
            throw new EntityNotFoundException(address);
        }
        return new Location(entity.address, entity.name, entity.phoneNumber);
    }
    public boolean addLocation (Location location){
        LocationEntity entity = new LocationEntity(location.getAddress(), location.getName(), location.getPhoneNumber());
        locationRepository.save(entity);
        return true;
    }
}

