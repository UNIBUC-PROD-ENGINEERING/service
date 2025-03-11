package ro.unibuc.hello.repository;

import ro.unibuc.hello.data.Librarian;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * No need to implement this interface.
 * Spring Data MongoDB automatically creates a class it implementing the interface when you run the application.
 */
@Repository
public interface LibrarianRepository extends MongoRepository<Librarian, String> {

    List<Librarian> findByEmailAndPassword(String email, String password);

}
