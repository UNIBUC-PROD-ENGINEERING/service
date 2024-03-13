package ro.unibuc.hello.service;

import java.io.Reader;
import java.time.LocalDate;
import java.util.List;

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
                            String phoneNumber, LocalDate birthDate, LocalDate registrationDate) {
        log.debug("Creating a new reader '{}' with nationality '{}'", name, nationality);
        var readerEntity = ReaderEntity.builder().name(name).nationality(nationality).email(email).phoneNumber(phoneNumber).birthDate(birthDate).registrationDate(registrationDate).build();
        return readerRepository.save(readerEntity);
    }

    public List<ReaderEntity> getAllReaders() {
        log.debug("Getting all readers");   
        return readerRepository.findAll();
        
    }

    public ReaderEntity updateReader(String id, String email, String phoneNumber) {
        log.debug("Updating the reader with id '{}', setting email '{}' and phone '{}'", id, email, phoneNumber);
        var reader = readerRepository.findById(id).get();
        reader.setEmail(email);
        reader.setPhoneNumber(phoneNumber);
        return readerRepository.save(reader);
    }

}
