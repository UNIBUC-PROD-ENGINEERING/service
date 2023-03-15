package ro.unibuc.hello.service;

import ro.unibuc.hello.data.*;
import ro.unibuc.hello.dto.CinemaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Component;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.management.Query;


@Component 
public class CinemaService{
    @Autowired
    private CinemaRepository cinemaRepository;

    public CinemaDTO addCinema(CinemaDTO cinemaDTO){
        Cinema cinema = cinemaDTO.toCinema(false);
        cinemaRepository.save(cinema);

        return new CinemaDTO(cinema);
    }

    public List<CinemaDTO> getCinemas(){
        return cinemaRepository.findAll()
        .stream().map(cinema -> new CinemaDTO(cinema))
        .collect(Collectors.toList());
    }

    public CinemaDTO getCinemaById(String id) throws EntityNotFoundException{
        Optional<Cinema> cinema = cinemaRepository.findById(id);

        if (cinema.isEmpty()){
            throw new EntityNotFoundException("cinema");
        }

        return new CinemaDTO(cinema.get());
    }

    public CinemaDTO updateCinema (CinemaDTO cinemaUpdateDTO)throws EntityNotFoundException{
        Optional<Cinema> oldCinema = cinemaRepository.findById(cinemaUpdateDTO.getId());
        if (oldCinema.isEmpty()){
            throw new EntityNotFoundException("cinema");
        }
        
        Cinema newCinema = oldCinema.get();
        newCinema.setName(cinemaUpdateDTO.getName());
        newCinema.setEmail(cinemaUpdateDTO.getEmail());
        newCinema.setStreet(cinemaUpdateDTO.getStreet());
        newCinema.setNumber(cinemaUpdateDTO.getNumber());
        newCinema.setCity(cinemaUpdateDTO.getCity());

        cinemaRepository.save(newCinema);

        return new CinemaDTO(newCinema);
    }

    public CinemaDTO getCinemaByName(String name)throws EntityNotFoundException{
        Optional <Cinema> cinema = cinemaRepository.findByName(name);
        
        if (cinema.isEmpty()){
            throw new EntityNotFoundException("cinema");

        }

        return new CinemaDTO(cinema.get());

    }

    public CinemaDTO getCinemaByCity(String city)throws EntityNotFoundException{
        Optional <Cinema> cinema = cinemaRepository.findByCity(city);
        
        if (cinema.isEmpty()){
            throw new EntityNotFoundException("cinema");

        }

        return new CinemaDTO(cinema.get());

    }
}
