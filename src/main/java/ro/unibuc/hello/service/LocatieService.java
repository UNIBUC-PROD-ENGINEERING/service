package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.unibuc.hello.dto.Locatie;
import ro.unibuc.hello.data.LocatieEntity;
import ro.unibuc.hello.data.LocatieRepository;
import ro.unibuc.hello.exception.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class LocatieService {
    @Autowired
    private LocatieRepository locatieRepository;

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
        return locatieRepository.findById(id).map(existingLocatie ->{
            existingLocatie.setTara(locatieDetails.getTara());
            existingLocatie.setOras(locatieDetails.getOras());
            existingLocatie.setStrada(locatieDetails.getStrada());

            return locatieRepository.save(existingLocatie);
        });
    }
    
   
}
