package ro.unibuc.hello.data;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TicketRespository extends MongoRepository<TicketEntity, String> {
    Optional<TicketEntity> findById(String id);
}
