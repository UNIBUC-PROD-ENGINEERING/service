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

import ro.unibuc.hello.data.DoctorEntity;
import ro.unibuc.hello.service.DoctorService;

@RestController
@RequestMapping("/doctor")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

    @PostMapping
    public DoctorEntity createDoctor(@RequestBody DoctorEntity doctor) {
        return doctorService.createDoctor(doctor);
    }

    @GetMapping("/{id}")
    public DoctorEntity getDoctorById(@PathVariable String id) {
        return doctorService.getDoctorById(id);
    }

    @GetMapping
    public List<DoctorEntity> getAllDoctors() {
        return doctorService.getAllDoctori();
    }

    @PutMapping("/{id}")
    public DoctorEntity updateDoctor(@PathVariable String id, @RequestBody DoctorEntity doctor) {
        doctor.setId(id);
        return doctorService.updateDoctor(doctor);
    }

    @DeleteMapping("/{id}")
    public void deleteDoctor(@PathVariable String id) {
        doctorService.deleteDoctor(id);
    }
}
