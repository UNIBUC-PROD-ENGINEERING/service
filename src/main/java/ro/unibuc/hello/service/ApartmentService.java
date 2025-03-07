package ro.unibuc.hello.service;

import org.springframework.stereotype.Service;
import ro.unibuc.hello.data.ApartmentEntity;
import ro.unibuc.hello.repository.ApartmentRepository;
import java.util.List;
import java.util.Optional;

@Service
public class ApartmentService {
    private final ApartmentRepository apartmentRepository;

    public ApartmentService(ApartmentRepository apartmentRepository) {
        this.apartmentRepository = apartmentRepository;
    }

    public List<ApartmentEntity> getAllApartments() {
        return apartmentRepository.findAll();
    }

    public Optional<ApartmentEntity> getApartmentById(String id) {
        return apartmentRepository.findById(id);
    }

    public ApartmentEntity createApartment(ApartmentEntity apartment) {
        return apartmentRepository.save(apartment);
    }

    public void deleteApartment(String id) {
        apartmentRepository.deleteById(id);
    }
}
