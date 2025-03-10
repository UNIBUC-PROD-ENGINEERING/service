package ro.unibuc.hello.service;

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

// package service;

import dto.Apartament;
import java.util.List;
import java.util.stream.Collectors;

public class ApartamentService {
    private List<Apartament> apartamente;

    public ApartamentService(List<Apartament> apartamente) {
        this.apartamente = apartamente;
    }

    public List<Apartament> getAllApartamente(String tara, String oras) {
        return apartamente.stream()
                .filter(a -> (tara == null || a.getTara().equalsIgnoreCase(tara)))
                .filter(a -> (oras == null || a.getOras().equalsIgnoreCase(oras)))
                .collect(Collectors.toList());
    }
}
