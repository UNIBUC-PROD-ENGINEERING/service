package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.unibuc.hello.data.Car;
import ro.unibuc.hello.data.CarsRepository;
import ro.unibuc.hello.data.InformationRepository;
import ro.unibuc.hello.dto.CarDTO;

import java.util.List;

@Component
public class CarService {

	@Autowired
	private CarsRepository carsRepository;


	public Car findByNumarInmatriculare(String numarInmatriculare) {
		return carsRepository.findByNumarInmatriculare(numarInmatriculare);
	}

	public List<Car> findAllByParcarePlatita(boolean platit) {
		return carsRepository.findAllByParcarePlatita(platit);
	}

	public Car saveCar(Car car) {
		return carsRepository.save(car);
	}

	public List<Car> findAllByValabilitateParcareIsLessThan60() {
		return carsRepository.findAllByValabilitateParcareIsLessThan60();
	}
//	public List<Car> findAllByValabilitateParcareDesc() {
//		return carsRepository.findAllByValabilitateParcareDesc();
//	}
}
