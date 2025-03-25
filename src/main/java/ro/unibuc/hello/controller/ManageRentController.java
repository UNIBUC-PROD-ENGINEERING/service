package ro.unibuc.hello.controller;

import ro.unibuc.hello.data.Rent;
import ro.unibuc.hello.dto.LateRent;
import ro.unibuc.hello.service.ManageRentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manage")
public class ManageRentController {
    private final ManageRentService manageRentService;

    @Autowired
    public ManageRentController(ManageRentService manageRentService) {
        this.manageRentService = manageRentService;
    }

    @GetMapping("/rented")
    public ResponseEntity<List<Rent>> getAllActiveRents() {
        return ResponseEntity.ok(manageRentService.getAllActiveRents());
    }

    @GetMapping("/late")
    public ResponseEntity<List<LateRent>> getLateRenters() {
        return ResponseEntity.ok(manageRentService.getLateRents());
    }
}
