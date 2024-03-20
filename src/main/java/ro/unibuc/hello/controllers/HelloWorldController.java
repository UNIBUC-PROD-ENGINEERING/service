package ro.unibuc.hello.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.unibuc.hello.dtos.GreetingDTO;
import ro.unibuc.hello.exceptions.EntityNotFoundException;
import ro.unibuc.hello.services.HelloWorldService;

@Controller
public class HelloWorldController {

    @Autowired
    private HelloWorldService helloWorldService;

    @GetMapping("/hello-world")
    @ResponseBody
    public GreetingDTO sayHello(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) {
        return helloWorldService.hello(name);
    }

    @GetMapping("/info")
    @ResponseBody
    public GreetingDTO info(@RequestParam(name="title", required=false, defaultValue="Overview") String title) throws EntityNotFoundException {
        return helloWorldService.buildGreetingFromInfo(title);
    }

}
