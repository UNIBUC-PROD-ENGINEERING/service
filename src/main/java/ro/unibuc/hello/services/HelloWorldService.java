package ro.unibuc.hello.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.unibuc.hello.entities.InformationEntity;
import ro.unibuc.hello.repositories.InformationRepository;
import ro.unibuc.hello.dtos.GreetingDTO;
import ro.unibuc.hello.exceptions.EntityNotFoundException;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class HelloWorldService {

    @Autowired
    private InformationRepository informationRepository;

    private final AtomicLong counter = new AtomicLong();
    private static final String helloTemplate = "Hello, %s!";
    private static final String informationTemplate = "%s : %s!";

    public GreetingDTO hello(String name) {
        return new GreetingDTO(counter.incrementAndGet(), String.format(helloTemplate, name));
    }

    public GreetingDTO buildGreetingFromInfo(String title) throws EntityNotFoundException {
        InformationEntity entity = informationRepository.findByTitle(title);
        if (entity == null) {
            throw new EntityNotFoundException(title);
        }
        return new GreetingDTO(counter.incrementAndGet(), String.format(informationTemplate, entity.title, entity.description));
    }
}
