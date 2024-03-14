package ro.unibuc.hello.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.unibuc.hello.data.PacientEntity;
import ro.unibuc.hello.data.PacientRepository;
import ro.unibuc.hello.exception.EntityNotFoundException;

@Service
public class PacientService {
    @Autowired
    private PacientRepository pacientRepository;

    public PacientEntity createPacient(PacientEntity pacient) {
        pacient.setId(null);
        pacientRepository.save(pacient);
        return pacient;
    }

    public PacientEntity getPacientById(String id) {
        return pacientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("pacient"));    
    }

    public List<PacientEntity> getAllPacienti() {
        return pacientRepository.findAll();    
    }

    public PacientEntity updatePacient(PacientEntity pacient) {
        pacientRepository.findById(pacient.getId()).orElseThrow(() -> new EntityNotFoundException("pacient"));   
        pacientRepository.save(pacient);
        return pacient;
    }

    public void deletePacient(String id) {
        PacientEntity pacient = pacientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("pacient"));   
        pacientRepository.delete(pacient);
    }
}
