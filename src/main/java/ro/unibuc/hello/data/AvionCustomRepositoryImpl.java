package ro.unibuc.hello.data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import java.util.*;
import org.springframework.data.mongodb.core.query.*;

@Repository
public class AvionCustomRepositoryImpl implements AvionCustomRepository{
    @Autowired
    MongoTemplate mongoTemplate;

    public List<Avion> findAvionByProperties(String from, String to) {
        final Query query = new Query();
        query.fields().include("id").include("number").include("from").include("to");

        final List<Criteria> criteria = new ArrayList<>();
        if (from != null && !from.isEmpty())
            criteria.add(Criteria.where("from").is(from));
        if (to != null && !to.isEmpty())
            criteria.add(Criteria.where("to").is(to));

        if (!criteria.isEmpty())
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));
        return mongoTemplate.find(query, Avion.class);
    }
}
