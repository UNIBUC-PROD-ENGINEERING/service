package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.dto.InfoAvion;
import ro.unibuc.hello.data.Avion;
import ro.unibuc.hello.exception.DuplicateException;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.AvionService;

import java.util.List;

@Controller
public class AvionController {

    @Autowired
    private AvionService avionService;
    private static final String duplicateExceptionMessage = "An avion entity with the same number already exists so the state of the DB wasn't modified.";
    private static final String entityNotFoundExceptionMessage = "Avion entity with the requested number was not found so the state of the DB wasn't modified.";

    @GetMapping("/avion/{number}")
    @ResponseBody
    public ResponseEntity<?> getAvion(@PathVariable("number") String number) throws EntityNotFoundException {
        try {
            return ResponseEntity.ok().body(avionService.getAvionInfoByNumber(number));
        }
        catch (EntityNotFoundException exception) {
            return ResponseEntity.ok().body("Avion entity with the requested number not found.");
        }
    }

    @GetMapping("/avion")
    @ResponseBody
    public ResponseEntity<List<InfoAvion>> getAllAvioane() {
        return ResponseEntity.ok().body(avionService.getAllAvioane());
    }

    @PostMapping("/avion")
    @ResponseBody
    public ResponseEntity<?> addAvion(@RequestBody Avion avion) throws EntityNotFoundException {
        try {
            InfoAvion newAvion=avionService.addAvion(avion);
            return ResponseEntity.ok().body(newAvion);
        }
        catch (DuplicateException exception) {
            return ResponseEntity.ok().body(duplicateExceptionMessage);
        }
    }

    @DeleteMapping("/avion/{number}")
    @ResponseBody
    public ResponseEntity<?> removeAvion(@PathVariable("number") String number) throws EntityNotFoundException {
        try {
            return ResponseEntity.ok().body(avionService.removeAvion(number));
        }
        catch (EntityNotFoundException exception) {
            return ResponseEntity.ok().body(entityNotFoundExceptionMessage);
        }
    }

    @PutMapping("/avion/{number}")
    @ResponseBody
    public ResponseEntity<?> updateAvion(@PathVariable("number") String number, @RequestBody Avion avion) {
        try {
            return ResponseEntity.ok().body(avionService.updateAvion(number, avion));
        }
        catch (EntityNotFoundException exception) {
            return ResponseEntity.ok().body(entityNotFoundExceptionMessage);
        }
        catch (DuplicateException exception) {
            return ResponseEntity.ok().body(duplicateExceptionMessage);
        }
    }


    @GetMapping("/avionfilter")
    public ResponseEntity<?> getAvioaneByProperties(@RequestParam(name = "from",required = false) String from, @RequestParam(name = "to",required = false) String to) {
        return ResponseEntity.ok().body(avionService.fetchAvionByProperty(from, to));
    }
}

