package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import ro.unibuc.hello.data.Avion;
import ro.unibuc.hello.data.AvionRepository;
import ro.unibuc.hello.dto.InfoAvion;
import ro.unibuc.hello.exception.DuplicateException;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.concurrent.atomic.AtomicLong;
import java.util.*;

@Component
public class AvionService {

    @Autowired
    private AvionRepository avionRepository;
    @Autowired
    MongoTemplate mongoTemplate;

    private final AtomicLong counter = new AtomicLong();
    private static final String avionTemplate = "%s : %s -> %s";

    public InfoAvion getAvionInfoByNumber(String number) throws EntityNotFoundException {
        Avion entity = avionRepository.findByNumber(number);
        if (entity == null) {
            throw new EntityNotFoundException(number);
        }
        return new InfoAvion(String.format(avionTemplate,entity.number, entity.from, entity.to));
    }

    public List<InfoAvion> getAllAvioane() {
        List<Avion> entities = avionRepository.findAll();
        return convertAvionToInfoAvion(entities);
    }

    public InfoAvion addAvion(Avion avion)  throws DuplicateException {
        Avion dupEntity = avionRepository.findByNumber(avion.number);
        if (dupEntity != null) {
            throw new DuplicateException(avion.number);
        }
        Avion entity = avionRepository.save(avion);
        return new InfoAvion(String.format(avionTemplate,entity.number, entity.from, entity.to));
    }

    public InfoAvion removeAvion(String number) throws EntityNotFoundException  {
        Avion entity = avionRepository.findByNumber(number);
        if (entity == null) {
            throw new EntityNotFoundException(number);
        }
        avionRepository.deleteByNumber(number);
        return new InfoAvion(String.format(avionTemplate,entity.number, entity.from, entity.to));
    }

    public InfoAvion updateAvion(String number, Avion avion) {
        Avion entity = avionRepository.findByNumber(number);
        if (entity == null) {
            throw new EntityNotFoundException(number);
        }
        Avion dupEntity = avionRepository.findByNumber(avion.getNumber());
        if (dupEntity != null) {
            throw new DuplicateException(avion.number);
        }
        if(avion.getNumber()!=null && !entity.getNumber().equals(avion.getNumber())){
            entity.setNumber(avion.getNumber());
        }
        if(avion.getFrom()!=null && !entity.getFrom().equals(avion.getFrom())){
            entity.setFrom(avion.getFrom());
        }
        if(avion.getTo()!=null && !entity.getTo().equals(avion.getTo())){
            entity.setTo(avion.getTo());
        }

        Avion newEntity= avionRepository.save(entity);
        return new InfoAvion(String.format(avionTemplate,newEntity.number, newEntity.from, newEntity.to));
    }

    public List<InfoAvion> fetchAvionByProperty(String from, String to) {
        return convertAvionToInfoAvion(avionRepository.findAvionByProperties(from, to));
    }

    public List<InfoAvion> convertAvionToInfoAvion (List<Avion> avioane){
        List<InfoAvion> infoAvionList=new ArrayList<InfoAvion>();
        avioane.forEach( (avion -> infoAvionList.add(new InfoAvion(String.format(avionTemplate,avion.number, avion.from, avion.to)))));
        return infoAvionList;
    }
}
