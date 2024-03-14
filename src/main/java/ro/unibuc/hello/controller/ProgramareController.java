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

import ro.unibuc.hello.data.ProgramareEntity;
import ro.unibuc.hello.service.ProgramareService;

@RestController
@RequestMapping("/programare")
public class ProgramareController {
    @Autowired
    private ProgramareService programareService;

    @PostMapping
    public ProgramareEntity createProgramare(@RequestBody ProgramareEntity programare) {
        return programareService.createProgramare(programare);
    }

    @GetMapping("/{id}")
    public ProgramareEntity getProgramareById(@PathVariable String id) {
        return programareService.getProgramareById(id);
    }

    @GetMapping
    public List<ProgramareEntity> getAllProgramari() {
        return programareService.getAllProgramari();
    }

    @PutMapping("/{id}")
    public ProgramareEntity updateProgramare(@PathVariable String id, @RequestBody ProgramareEntity programare) {
        programare.setId(id);
        return programareService.updateProgramare(programare);
    }

    @DeleteMapping("/{id}")
    public void deleteProgramare(@PathVariable String id) {
        programareService.deleteProgramare(id);
    }
}
