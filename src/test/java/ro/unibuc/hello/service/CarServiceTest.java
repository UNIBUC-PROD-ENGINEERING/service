package ro.unibuc.hello.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ro.unibuc.hello.data.Car;
import ro.unibuc.hello.data.CarsRepository;
import ro.unibuc.hello.data.InformationEntity;
import ro.unibuc.hello.data.InformationRepository;
import ro.unibuc.hello.dto.Greeting;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CarServiceTest {

	@Mock
	CarsRepository mockedCarRepository;

	@InjectMocks
	CarService carService = new CarService();


	Car car = new Car("1","B100ABC", "60", "Audi", "A5", false);


	@Test
	void test_findByNrInmatriculare() {

		String title = "B100ABC";
		Car car1 = new Car("1","B100ABC", "60", "Audi", "A5", false);

		when(mockedCarRepository.findByNumarInmatriculare(title)).thenReturn(car1);

		Assertions.assertSame("B100ABC", carService.findByNumarInmatriculare(title).getNumarInmatriculare());
	}

	@Test
	void test_findAllByParcarePlatita() {

		List<Car> cars = carService.findAllByParcarePlatita(false);

		for (Car car1 : cars) {
			if (!car1.getParcarePlatita()) {
				Assertions.assertSame(car, car1);
			}
		}
	}

	@Test
	void test_saveCar() {


	}
//	@Test
//	void test_Model() {
//
//		Assertions.assertSame("I30", carService.saveCar());
//	}
//
//	@Test
//	void test_parcarePlatita() {
//
//		Assertions.assertSame(true, car.getParcarePlatita());
//	}


}