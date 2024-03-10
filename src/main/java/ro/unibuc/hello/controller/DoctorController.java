package ro.unibuc.hello.controller;

import java.util.List;

import javax.print.Doc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ro.unibuc.hello.data.DoctorEntity;
import ro.unibuc.hello.data.DoctorRepository;

@RestController
@RequestMapping("/doctor")
public class DoctorController {
    

    @Autowired
    private DoctorRepository doctorRepository;


    @PostMapping
    public DoctorEntity createDoctorEntity(@RequestBody DoctorEntity doctor)
    {
        return doctorRepository.save(doctor);
    }

    @GetMapping("/{id}")
    public DoctorEntity getDoctorById(@PathVariable String id)
    {
        return doctorRepository.findById(id).orElse(null);
    }

    @GetMapping
    public List<DoctorEntity> getAllDoctors()
    {
        return doctorRepository.findAll();
    }

    @PutMapping("/{id}")
    public DoctorEntity updateDoctor(@PathVariable String id, @RequestBody DoctorEntity doctor)
    {
        doctor.setId(id);
        return doctorRepository.save(doctor);
    }

    @DeleteMapping("/{id}")
    public void deleteDoctor(@PathVariable String id) {
        doctorRepository.deleteById(id);
    }

}
