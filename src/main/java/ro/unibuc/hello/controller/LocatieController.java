package main.java.ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.unibuc.hello.dto.locatii;
import main.java.ro.unibuc.hello.data.LocatieEntity;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.LocatieService;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/locatie")
public class LocatieController {

    @Autowired
    private LocatieService locatieService;

    public LocatieController(LocatieService locatieService){
        this.locatieService = locatieService;
    }

    private Locatie convertToDto(LocatieEntity entity) {
        return new locatie(entity.getId(), entity.getTara(), entity.getOras(), entity.getStrada());
    }
    
    @GetMapping
    public List<LocatieEntity> getAlllocatii(){
        return locatieService.getAlllocatii()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Locatie getlocatieById(@PathVariable String id){
        Optional<locatieEntity> locatie = locatieService.getLocatieById(id);
        return locatie.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseBody
    public Locatie createLocatie(@RequestBody Locatie locatie) {
        return locatieService.createLocatie(locatie);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public Locatie updateLocatie(@PathVariable String id, @RequestBody Locatie locatie) throws EntityNotFoundException{
        return locatieService.updateLocatie(id, locatie);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteLocatie(@PathVariable String id) throws EntityNotFoundException{
        locatieService.deleteLocatie(id);
    }
    
}
