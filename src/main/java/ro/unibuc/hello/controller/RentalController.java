package ro.unibuc.hello.controller;

import ro.unibuc.hello.data.Rental;
import ro.unibuc.hello.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/rentals")
public class RentalController {

    private final RentalService rentalService;

    @Autowired
    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping("/rent")
    public ResponseEntity<Rental> rentGame(@RequestParam String userId, @RequestParam String gameId) {
        Rental rental = rentalService.rentGame(userId, gameId);
        return new ResponseEntity<>(rental, HttpStatus.CREATED);
    }

    @PostMapping("/return")
    public ResponseEntity<Rental> returnGame(@RequestParam String userId, @RequestParam String gameId) {
        Rental rental = rentalService.returnGame(userId, gameId);
        return new ResponseEntity<>(rental, HttpStatus.OK);
    }
}
