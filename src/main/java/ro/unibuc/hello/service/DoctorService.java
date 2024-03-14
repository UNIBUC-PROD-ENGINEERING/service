package ro.unibuc.hello.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.unibuc.hello.data.DoctorEntity;
import ro.unibuc.hello.data.DoctorRepository;
import ro.unibuc.hello.exception.EntityNotFoundException;

@Service
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;

    public DoctorEntity createDoctor(DoctorEntity doctor) {
        doctor.setId(null);
        doctorRepository.save(doctor);
        return doctor;
    }

    public DoctorEntity getDoctorById(String id) {
        return doctorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("doctor"));    
    }

    public List<DoctorEntity> getAllDoctori() {
        return doctorRepository.findAll();    
    }

    public DoctorEntity updateDoctor(DoctorEntity doctor) {
        doctorRepository.findById(doctor.getId()).orElseThrow(() -> new EntityNotFoundException("doctor"));   
        doctorRepository.save(doctor);
        return doctor;
    }

    public void deleteDoctor(String id) {
        DoctorEntity doctor = doctorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("doctor"));   
        doctorRepository.delete(doctor);
    }
}
