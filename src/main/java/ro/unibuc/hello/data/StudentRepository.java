package ro.unibuc.hello.data;

import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface StudentRepository extends MongoRepository<Student, String> {
}