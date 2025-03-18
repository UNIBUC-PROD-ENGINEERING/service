package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.unibuc.hello.dto.Proprietar;
import ro.unibuc.hello.data.ProprietarEntity;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.ProprietarService;
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
        return new Proprietar(entity.getId(), entity.getNume(), entity.getPrenume(), entity.getEmail());
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
    @ResponseBody
    public Proprietar createProprietar(@RequestBody Proprietar proprietar) {
        return proprietarService.createProprietar(proprietar);
    }

    @PutMapping("/api/proprietar/{id}")
    public ResponseEntity<Proprietar> updateProprietar(@PathVariable String id, @RequestBody Proprietar proprietar) throws EntityNotFoundException {
        ProprietarEntity proprietarEntity = new ProprietarEntity(
                proprietar.getId(),
                proprietar.getNume(),
                proprietar.getPrenume(),
                proprietar.getEmail()
        );
        
        Optional<ProprietarEntity> updatedEntity = proprietarService.updateProprietar(id, proprietarEntity);
        
        return updatedEntity.map(entity -> ResponseEntity.ok(convertToDto(entity)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
}
