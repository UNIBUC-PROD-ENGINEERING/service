package ro.unibuc.hello.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.unibuc.hello.data.ProgramareEntity;
import ro.unibuc.hello.data.ProgramareRepository;
import ro.unibuc.hello.exception.EntityNotFoundException;

@Service
public class ProgramareService {
    @Autowired
    private ProgramareRepository programareRepository;

    public ProgramareEntity createProgramare(ProgramareEntity programare) {
        programare.setId(null);
        programareRepository.save(programare);
        return programare;
    }

    public ProgramareEntity getProgramareById(String id) {
        return programareRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("programare"));
    }

    public List<ProgramareEntity> getAllProgramari() {
        return programareRepository.findAll();
    }

    public ProgramareEntity updateProgramare(ProgramareEntity programare) {
        programareRepository.findById(programare.getId()).orElseThrow(() -> new EntityNotFoundException("programare"));
        programareRepository.save(programare);
        return programare;
    }

    public void deleteProgramare(String id) {
        ProgramareEntity programare = programareRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("programare"));
        programareRepository.delete(programare);
    }
}
