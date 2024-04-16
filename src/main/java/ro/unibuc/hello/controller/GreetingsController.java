package ro.unibuc.hello.controller;

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

@Controller
public class GreetingsController {

    @Autowired
    private GreetingsService greetingsService;

    @GetMapping("/hello-world")
    @ResponseBody
    public Greeting sayHello(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) {
        return greetingsService.hello(name);
    }

    @GetMapping("/info")
    @ResponseBody
    public Greeting info(@RequestParam(name="title", required=false, defaultValue="Overview") String title) throws EntityNotFoundException {
        return greetingsService.buildGreetingFromInfo(title);
    }

    @GetMapping("/greetings")
    @ResponseBody
    public List<Greeting> getAllGreetings() {
        return greetingsService.getAllGreetings();
    }


    @PostMapping("/greetings")
    @ResponseBody
    public Greeting createGreeting(@RequestBody Greeting greeting) {
        return greetingsService.saveGreeting(greeting);
    }

    @PutMapping("/greetings/{id}")
    @ResponseBody
    public Greeting updateGreeting(@PathVariable String id, @RequestBody Greeting greeting) throws EntityNotFoundException {
        return greetingsService.updateGreeting(id, greeting);
    }

    @DeleteMapping("/greetings/{id}")
    @ResponseBody
    public void deleteGreeting(@PathVariable String id) throws EntityNotFoundException {
        greetingsService.deleteGreeting(id);
    }
}

