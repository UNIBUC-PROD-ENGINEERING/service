package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.unibuc.hello.dto.Locatie;
import ro.unibuc.hello.dto.Proprietar;
import ro.unibuc.hello.data.LocatieEntity;
import ro.unibuc.hello.data.ProprietarEntity;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.LocatieService;
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
    public ResponseEntity<Locatie> getLocatieById(@PathVariable String id){
        return locatieService.getLocatieById(id)
                .map(this::convertToDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping("/api/locatie")
    @ResponseBody
    public Locatie createLocatie(@RequestBody Locatie locatie) {
        return locatieService.createLocatie(locatie);
    }

    @PutMapping("/api/locatie/{id}")
    public ResponseEntity<Locatie> updateLocatie(@PathVariable String id, @RequestBody Locatie locatie) throws EntityNotFoundException {
        LocatieEntity locatieEntity = new LocatieEntity(
                locatie.getId(),
                locatie.getTara(),
                locatie.getOras(),
                locatie.getStrada()
        );
        
        Optional<LocatieEntity> updatedEntity = locatieService.updateLocatie(id, locatieEntity);
        
        return updatedEntity.map(entity -> ResponseEntity.ok(convertToDto(entity)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    
}
