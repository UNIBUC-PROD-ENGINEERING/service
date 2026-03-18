package ro.unibuc.prodeng.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import ro.unibuc.prodeng.model.Masina;

@Repository
public interface MasinaRepository extends MongoRepository<Masina, String> {
    boolean existsByMarcaAndModelAndAn(String marca, String model, int an);
    boolean existsByMarcaAndModelAndAnAndIdNot(String marca, String model, int an, String id);
    
    List<Masina> findByMarca(String marca);
}
