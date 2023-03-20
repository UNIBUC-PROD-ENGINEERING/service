package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import ro.unibuc.hello.service.CinemaService;
import ro.unibuc.hello.dto.CinemaDTO;
import ro.unibuc.hello.data.Cinema;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.List;


@Controller
public class CinemaController {

    @Autowired
    private CinemaService cinemaService;

    @PostMapping("cinema/add_cinema")
    @ResponseBody
    public CinemaDTO addCinema(@RequestBody CinemaDTO cinemaDTO){
        return cinemaService.addCinema(cinemaDTO);
    }

    @GetMapping("/cinemas")
    @ResponseBody
    public List<CinemaDTO> getCinemas(){
        return cinemaService.getCinemas();
    } 

    @GetMapping("cinema/{cinemaId}")
    @ResponseBody
    public CinemaDTO getCinemaById(@PathVariable(value = "cinemaId") String id) throws EntityNotFoundException{
        return cinemaService.getCinemaById(id);
    }

    @PutMapping("cinema/update_cinema")
    @ResponseBody
    public CinemaDTO updateCinema(@RequestBody CinemaDTO cinemaUpdatedDTO){
        return cinemaService.updateCinema(cinemaUpdatedDTO);
    }

    @GetMapping("cinema/name/{name}")
    @ResponseBody
    public ResponseEntity<Cinema> getCinemaByName(@PathVariable(value = "name") String name) throws EntityNotFoundException{
        return cinemaService.getCinemaByName(name);
    }

    @GetMapping("cinema/city/{city}")
    @ResponseBody
    public ResponseEntity<Cinema> getCinemaByCity(@PathVariable(value = "city") String city) throws EntityNotFoundException{
        return cinemaService.getCinemaByCity(city);
    }
}
