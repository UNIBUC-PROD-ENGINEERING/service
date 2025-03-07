package ro.unibuc.hello.controller;

import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.data.ApartmentEntity;
import ro.unibuc.hello.service.ApartmentService;

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
    public ApartmentEntity createApartment(@RequestBody ApartmentEntity apartment) {
        return apartmentService.createApartment(apartment);
    }

    @DeleteMapping("/{id}")
    public void deleteApartment(@PathVariable String id) {
        apartmentService.deleteApartment(id);
    }
}
