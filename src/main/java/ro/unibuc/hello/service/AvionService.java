package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.unibuc.hello.data.Avion;
import ro.unibuc.hello.data.AvionRepository;
import ro.unibuc.hello.dto.InfoAvion;
import ro.unibuc.hello.exception.DuplicateException;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class AvionService {

    @Autowired
    private AvionRepository avionRepository;

    private final AtomicLong counter = new AtomicLong();
    private static final String avionTemplate = "%s : %s -> %s";

    public InfoAvion getAvionInfoByNumber(String number) throws EntityNotFoundException {
        Avion entity = avionRepository.findByNumber(number);
        if (entity == null) {
            throw new EntityNotFoundException(number);
        }
        return new InfoAvion(counter.incrementAndGet(), String.format(avionTemplate,entity.number, entity.from, entity.to));
    }

    public InfoAvion addAvion(Avion avion)  throws DuplicateException {
        Avion dupEntity = avionRepository.findByNumber(avion.number);
        if (dupEntity != null) {
            throw new DuplicateException(avion.number);
        }
        Avion entity = avionRepository.save(avion);
        return new InfoAvion(counter.incrementAndGet(), String.format(avionTemplate,entity.number, entity.from, entity.to));
    }

    public void removeAvion(String number) throws EntityNotFoundException  {
        Avion entity = avionRepository.findByNumber(number);
        if (entity == null) {
            throw new EntityNotFoundException(number);
        }
        avionRepository.deleteByNumber(number);
    }

}
