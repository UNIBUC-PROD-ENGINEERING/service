package main.java.ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.unibuc.hello.dto.Proprietar;
import main.java.ro.unibuc.hello.data.ProprietarEntity;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.ProprietarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/proprietar")
public class ProprietarController {

    @Autowired
    private ProprietarService proprietarService;

    public ProprietarController(ProprietarService proprietarService){
        this.proprietarService = proprietarService;
    }

    private Proprietar convertToDto(ProprietarEntity entity) {
        return new Proprietar(entity.getId(), entity.getNume(), entity.getPrenume(), entity.getEmail());
    }
    
    @GetMapping
    public List<ProprietarEntity> getAllProprietari(){
        return proprietarService.getAllProprietari()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Proprietar getProprietarById(@PathVariable String id){
        Optional<ProprietarEntity> proprietar = proprietarService.getProprietarById(id);
        return proprietar.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseBody
    public Proprietar createProprietar(@RequestBody Proprietar proprietar) {
        return proprietarService.createProprietar(proprietar);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public Proprietar updateProprietar(@PathVariable String id, @RequestBody Proprietar proprietar) throws EntityNotFoundException{
        return proprietarService.updateProprietar(id, proprietar);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteProprietar(@PathVariable String id) throws EntityNotFoundException{
        proprietarService.deleteProprietar(id);
    }
    
}
