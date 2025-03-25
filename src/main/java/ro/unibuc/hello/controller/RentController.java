package ro.unibuc.hello.controller;

import jakarta.validation.Valid;
import ro.unibuc.hello.data.Rent;
import ro.unibuc.hello.dto.RentRequest;
import ro.unibuc.hello.service.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rent")
public class RentController {

    private final RentService rentService;

    @Autowired
    public RentController(RentService rentService) {
        this.rentService = rentService;
    }

    @GetMapping
    public ResponseEntity<List<Rent>> getAllRents() {
        List<Rent> rents = rentService.getAllRents();
        return new ResponseEntity<>(rents, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rent> getRentById(@PathVariable String id) {
        Rent rent = rentService.getRentById(id);
        return new ResponseEntity<>(rent, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Rent>> getRentsByUserId(@PathVariable String userId) {
        List<Rent> rents = rentService.getRentsByUserId(userId);
        return new ResponseEntity<>(rents, HttpStatus.OK);
    }

    @GetMapping("/game/{gameId}")
    public ResponseEntity<List<Rent>> getRentsByGameId(@PathVariable String gameId) {
        List<Rent> rents = rentService.getRentsByGameId(gameId);
        return new ResponseEntity<>(rents, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Rent> rentGame(@Valid @RequestBody RentRequest request) {
        Rent rent = rentService.rentGame(request.getUserId(), request.getGameId());
        return new ResponseEntity<>(rent, HttpStatus.CREATED);
    }

    @PostMapping("/return")
    public ResponseEntity<Rent> returnGame(@Valid @RequestBody RentRequest request) {
        Rent rent = rentService.returnGame(request.getUserId(), request.getGameId());
        return new ResponseEntity<>(rent, HttpStatus.OK);
    }
}