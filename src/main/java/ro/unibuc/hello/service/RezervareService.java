package main.java.ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.unibuc.hello.data.InformationEntity;
import ro.unibuc.hello.data.InformationRepository;
import ro.unibuc.hello.dto.Greeting;
import ro.unibuc.hello.exception.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class RezervareService {

    @Autowired
    private RezervareRepository rezervareRepository;

    public List<RezervareDto> getAllReservations() {
        List<RezervareEntity> rezervari = rezervareRepository.findAll();
        return rezervari.stream()
                .map(rezervare -> new RezervareDto(rezervare.getId(), ))
                .collect(Collectors.toList());
    }
}
