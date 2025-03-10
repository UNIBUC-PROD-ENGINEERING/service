package main.java.ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.unibuc.hello.dto.Greeting;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.GreetingsService;

import org.springframework.web.bind.annotation.*;
import java.util.List;

// package controller;

import dto.Apartament;
import service.ApartamentService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/apartamente")
public class ApartamentController {
    private final ApartamentService apartamentService;

    public ApartamentController(ApartamentService apartamentService) {
        this.apartamentService = apartamentService;
    }

    @GetMapping
    public List<Apartament> getApartamente(@RequestParam(required = false) String tara,
                                           @RequestParam(required = false) String oras) {
        return apartamentService.getAllApartamente(tara, oras);
    }
}