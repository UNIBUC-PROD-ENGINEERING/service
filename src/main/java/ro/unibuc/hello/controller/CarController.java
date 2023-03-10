package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
}
