package ro.unibuc.slots.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.unibuc.slots.dto.Greeting;
import ro.unibuc.slots.exception.EntityNotFoundException;
import ro.unibuc.slots.service.HelloWorldService;

@Controller
public class HelloWorldController {
    private final HelloWorldService helloWorldService;

    public HelloWorldController(final HelloWorldService helloWorldService) {
        this.helloWorldService = helloWorldService;
    }

    @GetMapping("/hello-world")
    @ResponseBody
    public Greeting sayHello(@RequestParam(name="name", required=false, defaultValue="Stranger") final String name) {
        return helloWorldService.hello(name);
    }

    @GetMapping("/info")
    @ResponseBody
    public Greeting info(
            @RequestParam(name="title", required=false, defaultValue="Overview") final String title
    ) throws EntityNotFoundException {
        return helloWorldService.buildGreetingFromInfo(title);
    }
}
