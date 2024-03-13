package ro.unibuc.hello.service;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import ro.unibuc.hello.data.ReaderRepository;
import ro.unibuc.hello.data.ReaderEntity;


@Service
@RequiredArgsConstructor
@Slf4j
public class ReaderService {

    @Autowired
    private ReaderRepository readerRepository;

    public ReaderEntity saveReader(String name, String nationality, String email, 
                                                Integer phoneNumber, LocalDate birthDate, LocalDate registrationDate) {
        log.debug("Creating a new reader '{}' with nationality '{}'", name, nationality);
        var readerEntity = ReaderEntity.builder().name(name).nationality(nationality).email(email).phoneNumber(phoneNumber).birthDate(birthDate).registrationDate(registrationDate).build();
        return readerRepository.save(readerEntity);
    }

}
