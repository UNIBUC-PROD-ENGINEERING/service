package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.unibuc.hello.dto.Greeting;
import ro.unibuc.hello.dto.ProdusDTO;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.ProdusService;

import java.util.List;
import java.util.Optional;

@Controller
public class ProdusController {

    @Autowired
    private ProdusService produsService;

    @GetMapping("/getProdus")
    @ResponseBody
    public Optional<ProdusDTO> getProdus(@RequestParam(name="id", required=false) String id) {
        return produsService.getProdus(id);
    }
    @PostMapping("/createProdus")
    @ResponseBody
    public void createProdus( ProdusDTO produs) {
        produsService.createProdus(produs);
    }
    @GetMapping("/getAllProduse")
    @ResponseBody
    public List<ProdusDTO> getAll() {
        return produsService.getAll();
    }


}