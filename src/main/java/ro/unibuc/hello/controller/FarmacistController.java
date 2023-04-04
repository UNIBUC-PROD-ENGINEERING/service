package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.dto.Greeting;
import ro.unibuc.hello.dto.Farmacist;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.HelloWorldService;
import ro.unibuc.hello.service.FarmacistService;

import java.util.ArrayList;

@Controller
public class FarmacistController {

    @Autowired
    private FarmacistService farmacistService;

    @GetMapping("/farmacisti")
    @ResponseBody
    public String getFarmacisti(@RequestParam(name="title", required=false, defaultValue="Farmacisti") String title)  throws EntityNotFoundException {
        return farmacistService.getFarmacisti(title);
    }

    @GetMapping("/farmacist/{id}")
    @ResponseBody
    public String getFarmacist(@PathVariable("id")long id, @RequestParam(name="title", required=false, defaultValue="Farmacisti") String title)  throws EntityNotFoundException {
        return farmacistService.getFarmacist(title,id).toString();
    }

    @PostMapping(value = "/addfarmacist", produces = "application/json")
    @ResponseBody
    public String addFarmacisti(@RequestBody Farmacist m) throws EntityNotFoundException {
        return farmacistService.addFarmacisti("Farmacisti",m.getName(),m.getEmail());
    }

    @DeleteMapping("/delfarmacist/{id}")
    @ResponseBody
    public String delFarmacist(@PathVariable("id")long id, @RequestParam(name="title", required=false, defaultValue="Farmacisti") String title)  throws EntityNotFoundException {
        farmacistService.delFarmacist(id,title);
        return "Deleted";
    }


}
