package ro.unibuc.prodeng.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import ro.unibuc.prodeng.exception.EntityNotFoundException;
import ro.unibuc.prodeng.request.MasinaRequest;
import ro.unibuc.prodeng.request.UpdateMasinaStatusRequest;
import ro.unibuc.prodeng.response.MasinaResponse;
import ro.unibuc.prodeng.service.MasinaService;

@RestController
@RequestMapping("/api/masini")
public class MasinaController {

    @Autowired
    private MasinaService masinaService;

    @GetMapping
    public ResponseEntity<List<MasinaResponse>> getAllMasini() {
        List<MasinaResponse> masini = masinaService.getAllMasini();
        return ResponseEntity.ok(masini);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MasinaResponse> getMasinaById(@PathVariable String id) throws EntityNotFoundException {
        MasinaResponse masina = masinaService.getMasinaById(id);
        return ResponseEntity.ok(masina);
    }

    @PostMapping
    public ResponseEntity<MasinaResponse> createMasina(@Valid @RequestBody MasinaRequest request) {
        MasinaResponse masina = masinaService.createMasina(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(masina);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MasinaResponse> updateMasina(
            @PathVariable String id, 
            @Valid @RequestBody MasinaRequest request) throws EntityNotFoundException {
        MasinaResponse masina = masinaService.updateMasina(id, request);
        return ResponseEntity.ok(masina);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateMasinaStatus(
            @PathVariable String id, 
            @Valid @RequestBody UpdateMasinaStatusRequest request) throws EntityNotFoundException {
        masinaService.updateStatus(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMasina(@PathVariable String id) throws EntityNotFoundException {
        masinaService.deleteMasina(id);
        return ResponseEntity.noContent().build();
    }
}
