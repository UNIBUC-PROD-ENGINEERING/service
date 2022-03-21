package ro.unibuc.hello.controller;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.unibuc.hello.data.InformationEntity;
import ro.unibuc.hello.data.InformationRepository;
import ro.unibuc.hello.dto.Greeting;

import java.util.concurrent.atomic.AtomicLong;

@Controller
public class HelloWorldController {

    @Autowired
    private InformationRepository informationRepository;

    @Autowired
    MeterRegistry metricsRegistry;

    private static final String helloTemplate = "Hello, %s!";
    private static final String informationTemplate = "%s : %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/hello-world")
    @ResponseBody
    @Timed(value = "hello.greeting.time", description = "Time taken to return greeting")
    @Counted(value = "hello.greeting.count", description = "Times greeting was returned")
    public Greeting sayHello(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) {
        metricsRegistry.counter("my_non_aop_metric", "endpoint", "hello").increment(counter.incrementAndGet());
        return new Greeting(counter.incrementAndGet(), String.format(helloTemplate, name));
    }

    @GetMapping("/info")
    @ResponseBody
    @Timed(value = "hello.info.time", description = "Time taken to return info")
    @Counted(value = "hello.info.count", description = "Times info was returned")
    public Greeting listAll(@RequestParam(name="title", required=false, defaultValue="Overview") String title) {
        InformationEntity entity = informationRepository.findByTitle(title);
        return new Greeting(counter.incrementAndGet(), String.format(informationTemplate, entity.title, entity.description));
    }

}
