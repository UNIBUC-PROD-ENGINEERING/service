package ro.unibuc.hello.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarsRepository extends MongoRepository<Car, String> {

	Car findByNumarInmatriculare(String numarInmatriculare);

	List<Car> findAllByParcarePlatita(boolean platit);

	List<Car> findAllByValabilitateParcareIsLessThan60();


}
