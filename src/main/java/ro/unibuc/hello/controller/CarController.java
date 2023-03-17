package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.data.Car;
import ro.unibuc.hello.service.CarService;

import java.util.List;

@Controller
public class CarController {

	@Autowired
	private CarService carService;

	@GetMapping("/numar-inmatriculare")
	@ResponseBody
	public Car checkByNumarInmatriculare(@RequestParam(name="numar", required=false, defaultValue="B100ABC") String numar) {
		return carService.findByNumarInmatriculare(numar);
	}

	@GetMapping("/parcare-platita")
	@ResponseBody
	public List<Car> getParcarePlatita(@RequestParam(name="platit", required=false, defaultValue="true") Boolean platit) {
		return carService.findAllByParcarePlatita(platit);
	}

	@GetMapping("/valabilitate-mica")
	@ResponseBody
	public List<Car> getParcarePlatita() {
		return carService.findAllByValabilitateParcareIsLessThan60();
	}

	@PostMapping("/save")
	public ResponseEntity<Car> saveCar(@RequestBody Car car) {
		return new ResponseEntity<>(carService.saveCar(car), HttpStatus.OK);
	}
}
