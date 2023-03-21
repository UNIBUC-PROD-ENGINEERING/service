package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.unibuc.hello.data.InformationEntity;
import ro.unibuc.hello.data.InformationRepository;
import ro.unibuc.hello.data.FarmacistEntity;
import ro.unibuc.hello.data.FarmacistRepository;
import ro.unibuc.hello.dto.Farmacist;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;

@Component
public class FarmacistService {

    @Autowired
    private FarmacistRepository farmacistRepository;

    public String getFarmacisti(String name){
        FarmacistEntity entity = farmacistRepository.findByName(name);
        if (entity == null) {
            throw new EntityNotFoundException(name);
        }
        return entity.getFarmacisti().toString();
    }

    public Farmacist getFarmacist(String name, long id){

        FarmacistEntity entity = farmacistRepository.findByName(name);
        if (entity == null) {
            throw new EntityNotFoundException(name);
        }
        return entity.getFarmacist(id);
    }

    public void delFarmacist(long id, String name){

        FarmacistEntity entity = farmacistRepository.findByName(name);
        if (entity == null) {
            throw new EntityNotFoundException(name);
        }
        ArrayList<Farmacist> mednew = entity.delFarmacist(id);
        entity.setFarmacisti(mednew);
        farmacistRepository.deleteAll();
        farmacistRepository.save(entity);
    }

    public String addFarmacisti(String db, String name, String email){

        Farmacist m = new Farmacist(name,email);
        FarmacistEntity entity = farmacistRepository.findByName(db);

        if (entity == null) {
            throw new EntityNotFoundException(db);
        }

        entity.addFarmacist(m);
        farmacistRepository.save(entity);

        return Arrays.toString(new ArrayList[]{entity.getFarmacisti()});
    }
}
