package ro.unibuc.hello.data;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.hello.data.Donation;

@Repository
public interface DonationRepository extends MongoRepository<Donation, String> {
    // returns all Donations from one sender.
    List<Donation> findBySender(String sender);

    // returns all donations that are not anonymous
    List<Donation> findByAnonymity(boolean anonymity);
}