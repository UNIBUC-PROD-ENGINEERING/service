package ro.unibuc.contact.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.unibuc.contact.data.InformationEntity;
import ro.unibuc.contact.data.InformationRepository;
import ro.unibuc.contact.dto.Greeting;
import ro.unibuc.contact.exception.EntityNotFoundException;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class HelloWorldService {

    @Autowired
    private InformationRepository informationRepository;

    private final AtomicLong counter = new AtomicLong();
    private static final String helloTemplate = "Hello, %s!";
    private static final String informationTemplate = "%s : %s!";

    public Greeting hello(String name) {
        return new Greeting(counter.incrementAndGet(), String.format(helloTemplate, name));
    }

    public Greeting buildGreetingFromInfo(String title) throws EntityNotFoundException {
        InformationEntity entity = informationRepository.findByTitle(title);
        if (entity == null) {
            throw new EntityNotFoundException(title);
        }
        return new Greeting(counter.incrementAndGet(), String.format(informationTemplate, entity.title, entity.description));
    }
}
