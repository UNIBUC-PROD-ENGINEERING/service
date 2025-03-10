package main.java.ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import main.java.ro.unibuc.hello.dto.Locatie;
import main.java.ro.unibuc.hello.data.LocatieEntity;
import ro.unibuc.hello.exception.EntityNotFoundException;
import main.java.ro.unibuc.hello.service.LocatieService;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class LocatieController {

    @Autowired
    private LocatieService locatieService;

    public LocatieController(LocatieService locatieService){
        this.locatieService = locatieService;
    }

    private Locatie convertToDto(LocatieEntity entity) {
        return new Locatie(entity.getId(), entity.getTara(), entity.getOras(), entity.getStrada());
    }
    
    @GetMapping("/api/locatie")
    public List<Locatie> getAllLocatii() {
        return locatieService.getAllLocatii()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/api/locatie/{id}")
    @ResponseBody
    public Locatie getLocatieById(@PathVariable String id){
        Optional<locatieEntity> locatie = locatieService.getLocatieById(id);
        return locatie.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/api/locatie")
    @ResponseBody
    public Locatie createLocatie(@RequestBody Locatie locatie) {
        return locatieService.createLocatie(locatie);
    }

    @PutMapping("/api/locatie/{id}")
    @ResponseBody
    public Locatie updateLocatie(@PathVariable String id, @RequestBody Locatie locatie) throws EntityNotFoundException{
        return locatieService.updateLocatie(id, locatie);
    }

    
}
