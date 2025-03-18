package ro.unibuc.hello.data;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class UserSearchRepository {

    private final MongoTemplate mongoTemplate;

    public UserSearchRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<UserEntity> searchUsers(String keyword) {

        Query query = new Query();
    
        query.addCriteria(new Criteria().orOperator(
            Criteria.where("username").regex(keyword, "i"), // Matches "abcd" and "abcd1" (prefix match)
            Criteria.where("username").is(keyword) // Prioritize exact match
        ));
        
        query.with(Sort.by(Sort.Order.desc("score"))) // Sort by relevance
             .limit(10);

        return mongoTemplate.find(query, UserEntity.class);
    }
}