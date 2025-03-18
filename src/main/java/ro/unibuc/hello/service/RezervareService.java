package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.unibuc.hello.data.InformationEntity;
import ro.unibuc.hello.data.RezervareEntity;
import ro.unibuc.hello.data.RezervareRepository;
import ro.unibuc.hello.dto.Greeting;
import ro.unibuc.hello.dto.RezervareDto;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RezervareService {

    @Autowired
    private RezervareRepository rezervareRepository;

    public List<RezervareDto> getAllReservationsByOwnerId(String id_proprietar) {
        List<RezervareEntity> rezervari = rezervareRepository.findAllById(List.of(id_proprietar));
        return rezervari.stream()
                .map(rezervare -> new RezervareDto(rezervare.getId()))
                .collect(Collectors.toList());
    }

    public RezervareDto saveRezervare(RezervareDto rezervareReq) {
        RezervareEntity rezervare = new RezervareEntity();
        rezervare.setApartament(rezervareReq.getApartament());
        rezervare.setStartDate(rezervareReq.getStartDate());
        rezervare.setEndDate(rezervareReq.getEndDate());
        rezervare.setUser(rezervareReq.getUser());
        rezervareRepository.save(rezervare);
        return new RezervareDto(rezervare.getId());
    }

    public RezervareDto updateRezervare(String id_proprietar, String id_rezervare) {
        RezervareEntity rezervare = rezervareRepository.findById(id_rezervare)
                                    .orElseThrow(() -> new EntityNotFoundException(id_rezervare));

        rezervare.setActive(true);

        rezervareRepository.save(rezervare);
        
        return new RezervareDto(rezervare.getId());
    }
}
