package ro.unibuc.hello.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import ro.unibuc.hello.data.*;
import ro.unibuc.hello.dto.CarsDTO;

import java.util.ArrayList;
import java.util.List;

@Component
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public List<CarsDTO> getCars() {
        ArrayList<CarsDTO> carsDTOs = new ArrayList<>();

        carRepository.findAll().forEach(carEntity -> carsDTOs.add(new CarsDTO(carEntity)));
        return carsDTOs;
    }

    public CarsDTO getCar(String id) {
        CarEntity car = carRepository.findById(id).orElse(null);

        if (car!=null)
            return new CarsDTO(car);
        else
            return null;
    }

    public CarsDTO insertCar(String carId,
                             String carMaker,
                             String carType,
                             Integer carYear,
                             String carEuro,
                             Integer carPrice){
        CarEntity car=new CarEntity(carId,carMaker,carType,carYear,carEuro,carPrice);


        return new CarsDTO(carRepository.save(car));
    }

    public CarsDTO updateCar(String carId,
                            String carMaker,
                            String carType,
                            Integer carYear,
                            String carEuro,
                            Integer carPrice){
        CarEntity car= carRepository.findById(carId).orElse(null);
        if(car!=null)
        {
            if(carId!=null)
                car.setCarId(carId);
            if(carMaker!=null)
                car.setCarMaker(carMaker);
            if(carType!=null)
                car.setCarType(carType);
            if(carYear!=null)
                car.setCarYear(carYear);
            if(carEuro!=null)
                car.setCarEuro(carEuro);
            if(carPrice!=null)
                car.setCarPrice(carPrice);

            return new CarsDTO(carRepository.save(car));
        }
        else return null;
    }

        public String deleteCar(String id){
        carRepository.deleteById(id);

        return "Car with id " + id + " was deleted!";
    }

}
