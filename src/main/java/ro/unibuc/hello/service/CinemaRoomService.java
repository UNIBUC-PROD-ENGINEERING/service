package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.unibuc.hello.data.CinemaRoomRepository;
import ro.unibuc.hello.data.LocationEntity;
import ro.unibuc.hello.data.LocationRepository;
import ro.unibuc.hello.data.CinemaRoomEntity;
import ro.unibuc.hello.dto.Location;
import ro.unibuc.hello.dto.CinemaRoom;
import ro.unibuc.hello.exception.EntityNotFoundException;
@Component
public class CinemaRoomService {

    @Autowired
    private CinemaRoomRepository cinemaRoomRepository;

    public CinemaRoom getCinemaRoomByNumber(String number) throws EntityNotFoundException{
        CinemaRoomEntity entity = cinemaRoomRepository.findByNumber(number);
        if(entity == null){
            throw new EntityNotFoundException(number);
        }
        return  new CinemaRoom(entity.location, entity.number);
    }

}
