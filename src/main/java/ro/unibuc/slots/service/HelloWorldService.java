package ro.unibuc.slots.service;

import org.springframework.stereotype.Component;
import ro.unibuc.slots.data.InformationEntity;
import ro.unibuc.slots.data.InformationRepository;
import ro.unibuc.slots.dto.Greeting;
import ro.unibuc.slots.exception.EntityNotFoundException;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class HelloWorldService {
    private final InformationRepository informationRepository;

    private final AtomicLong counter = new AtomicLong();
    private static final String helloTemplate = "Hello, %s!";
    private static final String informationTemplate = "%s : %s!";

    public HelloWorldService(final InformationRepository informationRepository) {
        this.informationRepository = informationRepository;
    }

    public Greeting hello(final String name) {
        return new Greeting(counter.incrementAndGet(), String.format(helloTemplate, name));
    }

    public Greeting buildGreetingFromInfo(final String title) throws EntityNotFoundException {
        final InformationEntity entity = informationRepository.findByTitle(title);
        if (entity == null) {
            throw new EntityNotFoundException(title);
        }

        return new Greeting(
                counter.incrementAndGet(),
                String.format(informationTemplate, entity.title, entity.description)
        );
    }
}
