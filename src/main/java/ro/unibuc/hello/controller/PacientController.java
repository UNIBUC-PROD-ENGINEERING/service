package ro.unibuc.hello.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ro.unibuc.hello.data.PacientEntity;
import ro.unibuc.hello.service.PacientService;

@RestController
@RequestMapping("/pacient")
public class PacientController {
    @Autowired
    private PacientService pacientService;

    @PostMapping
    public PacientEntity createPacient(@RequestBody PacientEntity pacient) {
        return pacientService.createPacient(pacient);
    }

    @GetMapping("/{id}")
    public PacientEntity getPacientById(@PathVariable String id) {
        return pacientService.getPacientById(id);
    }

    @GetMapping
    public List<PacientEntity> getAllPacienti() {
        return pacientService.getAllPacienti();
    }

    @PutMapping("/{id}")
    public PacientEntity updatePacient(@PathVariable String id, @RequestBody PacientEntity pacient) {
        pacient.setId(id);
        return pacientService.updatePacient(pacient);
    }

    @DeleteMapping("/{id}")
    public void deletePacient(@PathVariable String id) {
        pacientService.deletePacient(id);
    }
}
