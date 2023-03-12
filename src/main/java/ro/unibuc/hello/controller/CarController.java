package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.CarService;
import ro.unibuc.hello.dto.CarsDTO;
import java.util.List;


@RestController
public class CarController {
    @Autowired
    public CarService carService;


    @GetMapping("/car/getAll")
    @ResponseBody
    public List<CarsDTO> getCars(){
        return carService.getCars();
    }

    @GetMapping("/car/get")
    @ResponseBody
    public CarsDTO getCar(@RequestParam(name="carId") String id) {
        return carService.getCar(id);
    }

    @PostMapping("/car/insert")
    @ResponseBody
    public CarsDTO insertCar(@RequestParam(name="carId") String carId,
                            @RequestParam(name="carMaker") String carMaker,
                            @RequestParam(name="carType") String carType,
                            @RequestParam(name="carYear") Integer carYear,
                            @RequestParam(name="carEuro") String carEuro,
                            @RequestParam(name="carPrice") Integer carPrice) {
        return carService.insertCar(carId, carMaker, carType, carYear, carEuro, carPrice);
    }

    @PutMapping("/car/update")
    @ResponseBody
    public CarsDTO updateCar(@RequestParam(name="carId") String carId,
                             @RequestParam(name="carMaker") String carMaker,
                             @RequestParam(name="carType") String carType,
                             @RequestParam(name="carYear") Integer carYear,
                             @RequestParam(name="carEuro") String carEuro,
                             @RequestParam(name="carPrice") Integer carPrice) {
        return carService.updateCar(carId, carMaker, carType, carYear, carEuro, carPrice);
    }

    @DeleteMapping("/car/delete")
    @ResponseBody
    public String deleteCar(@RequestParam(name="id") String id) {
        return carService.deleteCar(id);

    }


}
