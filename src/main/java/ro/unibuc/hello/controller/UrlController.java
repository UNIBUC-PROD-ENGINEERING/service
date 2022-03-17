package ro.unibuc.hello.controller;

import java.lang.ProcessBuilder.Redirect;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ro.unibuc.hello.data.InformationEntity;
import ro.unibuc.hello.data.InformationRepository;
import ro.unibuc.hello.dto.Greeting;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


import ro.unibuc.hello.data.Url;
import ro.unibuc.hello.data.UrlRepository;


@RestController
public class UrlController {

	@Autowired
	private UrlRepository urlRepository;

    @PostMapping(value = "/", consumes = "application/json")
    public Url create(@RequestBody String payload) {
        return urlRepository.save(new Url("test", "test"));
    }

    @DeleteMapping (value = "/", consumes = "application/json")
    public String destroy(@RequestBody String payload) {
        return payload;
    }

    @GetMapping(value = "/{short_url}")
    public RedirectView redirect (@PathVariable String short_url) {
        return new RedirectView(short_url);
    }
}

