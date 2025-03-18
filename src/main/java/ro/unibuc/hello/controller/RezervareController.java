package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.unibuc.hello.dto.RezervareDto;
import ro.unibuc.hello.service.RezervareService;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class RezervareController {

    @Autowired
    private RezervareService rezervareService;

    @GetMapping("/api/rezervare/{id_proprietar}")
    @ResponseBody
    public List<RezervareDto> getReservationsByOwnerId(@PathVariable String id_proprietar) {
        return rezervareService.getAllReservationsByOwnerId(id_proprietar);
    }

    @PostMapping("/api/rezervare")
    @ResponseBody
    public RezervareDto createRezervare(@RequestBody RezervareDto rezervare) {
        return rezervareService.saveRezervare(rezervare);
    }

    @PutMapping("/api/rezervare/proprietar/{id_proprietar}/rezervare/{id_rezervare}")
    @ResponseBody
    public RezervareDto updateRezervare(@PathVariable String id_proprietar, @PathVariable String id_rezervare) {
        return rezervareService.updateRezervare(id_proprietar, id_rezervare);
    }
}  

