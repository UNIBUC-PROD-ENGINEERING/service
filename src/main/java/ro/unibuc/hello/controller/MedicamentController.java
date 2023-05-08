package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.dto.Greeting;
import ro.unibuc.hello.dto.Medicament;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.HelloWorldService;
import ro.unibuc.hello.service.MedicamentService;

import java.util.ArrayList;

@Controller
public class MedicamentController {

    @Autowired
    private MedicamentService medicamentService;

    @GetMapping("/medicamente")
    @ResponseBody
    public String getMedicamente(@RequestParam(name="title", required=false, defaultValue="Medicamente") String title)  throws EntityNotFoundException {
        return medicamentService.getMedicamente(title);
    }

    @GetMapping("/medicament/{id}")
    @ResponseBody
    public String getMedicament(@PathVariable("id")long id, @RequestParam(name="title", required=false, defaultValue="Medicamente") String title)  throws EntityNotFoundException {
        return medicamentService.getMedicament(title,id).toString();
    }

    @PostMapping(value = "/addmedicament", produces = "application/json")
    @ResponseBody
    public String addMedicamente(@RequestBody Medicament m) throws EntityNotFoundException {
        return medicamentService.addMedicamente("Medicamente",m.getName(),m.getIngredients());
    }

    @DeleteMapping("/delmedicament/{id}")
    @ResponseBody
    public String delMedicament(@PathVariable("id")long id, @RequestParam(name="title", required=false, defaultValue="Medicamente") String title)  throws EntityNotFoundException {
        medicamentService.delMedicament(id,title);
        return "Deleted";
    }
    
    @DeleteMapping("/delmedicamente")
    @ResponseBody
    public String delMedicamente(@RequestParam(name="title", required=false, defaultValue="Medicamente") String title)  throws EntityNotFoundException {
        medicamentService.delMedicamente(title);
        return "Deleted";
    }

    @PutMapping(value = "/editmedicament/{id}", produces = "application/json")
    @ResponseBody
    public String editMedicament(@PathVariable("id")long id, @RequestBody Medicament m) throws EntityNotFoundException {
        medicamentService.editMedicament("Medicamente",m.getName(),m.getIngredients(), id);
        return "Edited";
    }


}
