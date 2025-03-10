package main.java.ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import main.java.ro.unibuc.hello.dto.Locatie;
import ro.unibuc.hello.data.LocatieEntity;
import ro.unibuc.hello.data.LocatieRepository;
import ro.unibuc.hello.exception.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class LocatieService {
    @Autowired
    private final LocatieRepository locatieRepository;

    public LocatieService(LocatieRepository locatieRepository){
        this.locatieRepository = locatieRepository;
    }

    public List<LocatieEntity> getAllLocatii(){
        return locatieRepository.findAll();
    }

    public Optional<LocatieEntity> getLocatieById(String id){
        return locatieRepository.findById(id);
    }

    public Locatie createLocatie(Locatie locatieDTO) {
        LocatieEntity locatieEntity = new LocatieEntity(
            locatieDTO.getId(), 
            locatieDTO.getTara(), 
            locatieDTO.getOras(), 
            locatieDTO.getStrada()
        );
        locatieEntity = locatieRepository.save(locatieEntity);
        return new Locatie(
            locatieEntity.getId(),
            locatieEntity.getTara(),
            locatieEntity.getOras(),
            locatieEntity.getStrada()
        );
    }

    public Optional<LocatieEntity> updateLocatie(String id, LocatieEntity locatieDetails){
        return locatieDetails.findById(id).map(existingLocatie ->{
            existingLocatie.setTara(LocatieDetails.getTara());
            existingLocatie.setOras(LocatieDetails.getOras());
            existingLocatie.setStrada(LocatieDetails.setStrada());
        });
    }
    
    public void deleteLocatie(String id) throws EntityNotFoundException {
        LocatieEntity locatie = locatieRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
        locatieRepository.delete(entity);
    }

    public void deleteAllLocatiei() {
        locatieRepository.deleteAll();
    }
}
