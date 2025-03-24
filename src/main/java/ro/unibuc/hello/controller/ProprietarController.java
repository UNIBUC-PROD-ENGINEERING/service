package main.java.ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
<<<<<<< Updated upstream
import org.springframework.web.bind.annotation.ResponseBody;
import main.java.ro.unibuc.hello.dto.Proprietar;
import main.java.ro.unibuc.hello.data.ProprietarEntity;
import ro.unibuc.hello.exception.EntityNotFoundException;
import main.java.ro.unibuc.hello.service.ProprietarService;
=======

import ro.unibuc.hello.dto.Proprietar;
import ro.unibuc.hello.data.ProprietarEntity;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.ProprietarService;
import ro.unibuc.hello.util.CNPValidator;
>>>>>>> Stashed changes
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class ProprietarController {

    @Autowired
    private ProprietarService proprietarService;

    public ProprietarController(ProprietarService proprietarService){
        this.proprietarService = proprietarService;
    }

    private Proprietar convertToDto(ProprietarEntity entity) {
        return new Proprietar(entity.getId(), entity.getNume(), entity.getPrenume(), entity.getEmail(), entity.getCnp());
    }
    
    @GetMapping("/api/proprietar")
    public List<Proprietar> getAllProprietari(){
        return proprietarService.getAllProprietari()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/api/proprietar/{id}")
    public ResponseEntity<Proprietar> getProprietarById(@PathVariable String id){
        return proprietarService.getProprietarById(id)
                .map(this::convertToDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/api/proprietar")
    public ResponseEntity<Proprietar> createProprietar(@RequestBody Proprietar proprietar) {
    try {
        Proprietar savedProprietar = proprietarService.createProprietar(proprietar);
        return ResponseEntity.ok(savedProprietar);
    } catch (IllegalArgumentException e) {
        return ResponseEntity.badRequest().build();
    }
    }


    @PutMapping("/api/proprietar/{id}")
    public ResponseEntity<Proprietar> updateProprietar(@PathVariable String id, @RequestBody Proprietar proprietar) {
    ProprietarEntity proprietarEntity = new ProprietarEntity(
            proprietar.getId(),
            proprietar.getNume(),
            proprietar.getPrenume(),
            proprietar.getEmail(),
            proprietar.getCnp()
    );

    try {
        Optional<ProprietarEntity> updatedEntity = proprietarService.updateProprietar(id, proprietarEntity);
        return updatedEntity.map(entity -> ResponseEntity.ok(convertToDto(entity)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    } catch (IllegalArgumentException e) {
        return ResponseEntity.badRequest().build();
    }
<<<<<<< Updated upstream

    
=======
    }

>>>>>>> Stashed changes
    
}
