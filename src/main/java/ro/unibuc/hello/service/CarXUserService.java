package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.unibuc.hello.data.CarRepository;
import ro.unibuc.hello.data.CarXUserEntity;
import ro.unibuc.hello.data.CarXUserRepository;
import ro.unibuc.hello.dto.BuyCarDTO;
import ro.unibuc.hello.dto.GetUserCarsDTO;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class CarXUserService {
    @Autowired
    private CarXUserRepository carXUserRepository;

    @Autowired
    private CarRepository carRepository;

    public String buyCar(BuyCarDTO model){
        carXUserRepository.save(new CarXUserEntity(model.getUserId(), model.getCarId()));

        return "Acquisition was completed successfully";
    }

    public List<GetUserCarsDTO> GetCarsByUser(String userId){
        List<GetUserCarsDTO> userCars = new ArrayList<>();
        List<String> carIds = new ArrayList<>();

        var userCar = carXUserRepository.findAll();

        for (var uc :
                userCar) {
            if (Objects.equals(uc.getUserId(), userId)){
                carIds.add(uc.getCarId());
            }
        }

        for (var carId :
                carIds) {
            var carMapped = carRepository.findById(carId.toString())
                    .map(car -> new GetUserCarsDTO(userId,car.getCarId(), car.getCarMaker(), car.getCarYear(), car.getCarPrice()))
                    .orElse(null) ;
            if(carMapped == null){
                throw new EntityNotFoundException(carId.toString());
            }
            userCars.add(carMapped);
        }

        return userCars;
    }

}
