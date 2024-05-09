package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.unibuc.hello.data.InformationEntity;
import ro.unibuc.hello.data.InformationRepository;
import ro.unibuc.hello.dto.Greeting;
import ro.unibuc.hello.exception.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class GreetingsService {

    @Autowired
    private InformationRepository informationRepository;

    private final AtomicLong counter = new AtomicLong();
    private static final String helloTemplate = "Hello, %s!";
    private static final String informationTemplate = "%s : %s!";

    public Greeting hello(String name) {
        return new Greeting(Long.toString(counter.incrementAndGet()), String.format(helloTemplate, name));
    }

    public Greeting buildGreetingFromInfo(String title) throws EntityNotFoundException {
        InformationEntity entity = informationRepository.findByTitle(title);
        if (entity == null) {
            throw new EntityNotFoundException(title);
        }
        return new Greeting(Long.toString(counter.incrementAndGet()), String.format(informationTemplate, entity.getTitle(), entity.getDescription()));
    }

    public List<Greeting> getAllGreetings() {
        List<InformationEntity> entities = informationRepository.findAll();
        return entities.stream()
                .map(entity -> new Greeting(entity.getId(), entity.getTitle()))
                .collect(Collectors.toList());
    }

    public Greeting getGreetingById(String id) throws EntityNotFoundException {
        Optional<InformationEntity> optionalEntity = informationRepository.findById(id);
        InformationEntity entity = optionalEntity.orElseThrow(() -> new EntityNotFoundException(id));
        return new Greeting(entity.getId(), entity.getTitle());
    }

    public Greeting saveGreeting(Greeting greeting) {
        InformationEntity entity = new InformationEntity();
        entity.setId(greeting.getId());
        entity.setTitle(greeting.getContent());
        informationRepository.save(entity);
        return new Greeting(entity.getId(), entity.getTitle());
    }

    public List<Greeting> saveAll(List<Greeting> greetings) {
        List<InformationEntity> entities = greetings.stream()
                .map(greeting -> {
                    InformationEntity entity = new InformationEntity();
                    entity.setId(greeting.getId());
                    entity.setTitle(greeting.getContent());
                    return entity;
                })
                .collect(Collectors.toList());

        List<InformationEntity> savedEntities = informationRepository.saveAll(entities);

        return savedEntities.stream()
                .map(entity -> new Greeting(entity.getId(), entity.getTitle()))
                .collect(Collectors.toList());
    }

    public Greeting updateGreeting(String id, Greeting greeting) throws EntityNotFoundException {
        InformationEntity entity = informationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
        entity.setTitle(greeting.getContent());
        informationRepository.save(entity);
        return new Greeting(entity.getId(), entity.getTitle());
    }

    public void deleteGreeting(String id) throws EntityNotFoundException {
        InformationEntity entity = informationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
        informationRepository.delete(entity);
    }

    public void deleteAllGreetings() {
        informationRepository.deleteAll();
    }
}
