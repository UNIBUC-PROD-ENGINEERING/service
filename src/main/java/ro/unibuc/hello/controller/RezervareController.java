package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import main.java.ro.unibuc.hello.dto.RezervareDto;
import main.java.ro.unibuc.hello.service.RezervareService;
import ro.unibuc.hello.exception.EntityNotFoundException;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class RezervareController {

    @Autowired
    private RezervareService rezervareService;

    @GetMapping("/api/rezervare/{id_proprietar}")
    @ResponseBody
    public List<RezervareDto> getReservationsByOwnerId(@PathVariable String id_proprietar) {
        return rezervareService.getAllReservations();
    }
}

