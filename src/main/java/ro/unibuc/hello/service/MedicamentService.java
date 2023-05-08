package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.unibuc.hello.data.InformationEntity;
import ro.unibuc.hello.data.InformationRepository;
import ro.unibuc.hello.data.MedicamentEntity;
import ro.unibuc.hello.data.MedicamentRepository;
import ro.unibuc.hello.dto.Medicament;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;

@Component
public class MedicamentService {

    @Autowired
    private MedicamentRepository medicamentRepository;

    public String getMedicamente(String name) throws EntityNotFoundException {
        MedicamentEntity entity = medicamentRepository.findByName(name);
        if (entity == null) {
            throw new EntityNotFoundException(name);
        }
        return entity.getMedicamente().toString();
    }

    public Medicament getMedicament(String name, long id) throws EntityNotFoundException {

        MedicamentEntity entity = medicamentRepository.findByName(name);
        if (entity == null) {
            throw new EntityNotFoundException(name);
        }
        return entity.getMedicament(id);
    }

    public void delMedicament(long id, String name){

        MedicamentEntity entity = medicamentRepository.findByName(name);
        if (entity == null) {
            throw new EntityNotFoundException(name);
        }
        ArrayList<Medicament> mednew = entity.delMedicament(id);
        entity.setMedicamente(mednew);
        medicamentRepository.deleteAll();
        medicamentRepository.save(entity);
    }

    public String editMedicament(String db, String name, String[] ingredients, long id){

        MedicamentEntity entity = medicamentRepository.findByName(db);
        Medicament med = null;
        if (entity == null) {
            throw new EntityNotFoundException(db);
        }
        for (Medicament m: entity.getMedicamente()) {
            if(m.getId()==id){
                med = m;
                m.setIngredients(ingredients);
                m.setName(name);
            }
        }
        medicamentRepository.save(entity);
        assert med != null;
        return med.toString();
    }

    public void delMedicamente(String name){

        MedicamentEntity entity = medicamentRepository.findByName(name);
        if (entity == null) {
            throw new EntityNotFoundException(name);
        }
        entity.delMedicamente();
        medicamentRepository.deleteAll();
        medicamentRepository.save(entity);
    }

    public String addMedicamente(String db, String name, String[] ingredients){

        Medicament m = new Medicament(name,ingredients);
        MedicamentEntity entity = medicamentRepository.findByName(db);

        if (entity == null) {
            throw new EntityNotFoundException(db);
        }

        entity.addMedicament(m);
        medicamentRepository.save(entity);

        //return Arrays.toString(new ArrayList[]{entity.getMedicamente()});
        return m.toString();
    }
}
