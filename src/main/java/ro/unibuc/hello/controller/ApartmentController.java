package ro.unibuc.hello.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.data.ApartmentEntity;
import ro.unibuc.hello.exception.InvalidInputException;
import ro.unibuc.hello.service.ApartmentService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/apartments")
public class ApartmentController {
    private final ApartmentService apartmentService;

    public ApartmentController(ApartmentService apartmentService) {
        this.apartmentService = apartmentService;
    }

    @GetMapping
    public List<ApartmentEntity> getAllApartments() {
        return apartmentService.getAllApartments();
    }

    @GetMapping("/{id}")
    public Optional<ApartmentEntity> getApartmentById(@PathVariable String id) {
        return apartmentService.getApartmentById(id);
    }

    @PostMapping
    public String createApartment(@RequestBody ApartmentEntity apartment) {
        try {
            apartmentService.createApartment(apartment);
            return "Apartment successfully created!";
        } catch (IllegalArgumentException e) {
            return e.getMessage(); // Mesaj de eroare pentru userId inexistent
        }
    }

    @DeleteMapping("/{id}")
    public void deleteApartment(@PathVariable String id) {
        apartmentService.deleteApartment(id);
    }

    @GetMapping("/available")
    public List<ApartmentEntity> getAvailableApartments(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return apartmentService.findAvailableApartments(startDate, endDate);
    }
    
    @GetMapping("/{id}/available")
    public boolean isApartmentAvailable(
            @PathVariable String id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return apartmentService.isApartmentAvailable(id, startDate, endDate);
    }

    // Endpoint: Apartamente pet-friendly
    @GetMapping("/pet-friendly")
    public List<ApartmentEntity> getPetFriendlyApartments() {
        return apartmentService.getPetFriendlyApartments();
    }

    // Endpoint: Apartamente după numărul de camere
    @GetMapping("/rooms/{numberOfRooms}")
    public List<ApartmentEntity> getApartmentsByNumberOfRooms(@PathVariable int numberOfRooms) {
        return apartmentService.getApartmentsByNumberOfRooms(numberOfRooms);
    }

    // Endpoint: Apartamente după numărul de băi
    @GetMapping("/bathrooms/{numberOfBathrooms}")
    public List<ApartmentEntity> getApartmentsByNumberOfBathrooms(@PathVariable int numberOfBathrooms) {
        return apartmentService.getApartmentsByNumberOfBathrooms(numberOfBathrooms);
    }

    // Endpoint: Apartamente cu suprafață minimă
    @GetMapping("/square-meters")
    public List<ApartmentEntity> getApartmentsByMinimumSquareMeters(@RequestParam Double minSquareMeters) {
        return apartmentService.getApartmentsByMinimumSquareMeters(minSquareMeters);
    }

    // Endpoint: Apartamente sub un preț maxim
    @GetMapping("/price")
    public List<ApartmentEntity> getApartmentsByMaxPrice(@RequestParam Double maxPrice) {
        return apartmentService.getApartmentsByMaxPrice(maxPrice);
    }

    // Endpoint: Apartamente cu o anumită facilitate
    @GetMapping("/amenities")
    public List<ApartmentEntity> getApartmentsByAmenity(@RequestParam String amenity) {
        return apartmentService.getApartmentsByAmenity(amenity);
    }

    // Endpoint: Apartamente unde fumatul este permis/interzis
    @GetMapping("/smoking")
    public List<ApartmentEntity> getApartmentsBySmokingAllowed(@RequestParam boolean smokingAllowed) {
        return apartmentService.getApartmentsBySmokingAllowed(smokingAllowed);
    }

   // Endpoint: apartamente după locație (case-insensitive)
    @GetMapping("/location")
    public List<ApartmentEntity> getApartmentsByLocation(@RequestParam String location) {
        return apartmentService.getApartmentsByLocation(location);
    }

    // Endpoint: Filtru combinat (preț și suprafață)
    @GetMapping("/filter")
    public List<ApartmentEntity> getApartmentsByPriceAndSquareMeters(
            @RequestParam Double minPrice,
            @RequestParam Double maxPrice,
            @RequestParam Double minSquareMeters,
            @RequestParam Double maxSquareMeters) {
        return apartmentService.getApartmentsByPriceAndSquareMeters(minPrice, maxPrice, minSquareMeters, maxSquareMeters);
    }

    // Gestionarea excepțiilor
    @ExceptionHandler(InvalidInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidInputException(InvalidInputException ex) {
        return ex.getMessage();
    }
}