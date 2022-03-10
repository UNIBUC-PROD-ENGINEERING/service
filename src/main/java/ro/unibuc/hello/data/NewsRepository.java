package ro.unibuc.hello.data;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends MongoRepository<News, String> {
    // returns all News which title contains input title.
    List<News> findByTitleContaining(String title);

    // returns all News with published having value as input published.
    List<News> findByPublished(boolean published);
}