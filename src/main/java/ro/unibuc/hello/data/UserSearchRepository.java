package ro.unibuc.hello.data;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
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
        TextCriteria textCriteria = TextCriteria.forDefaultLanguage().matching(keyword);

        Query query = new Query();
    
        // Combine text search (for relevance) and regex (for partial matches)
        query.addCriteria(new Criteria().orOperator(
            Criteria.where("username").regex(keyword, "i"), // Matches "abcd" and "abcd1" (prefix match)
            Criteria.where("username").is(keyword) // Prioritize exact match
        ));
        
        // Apply text search scoring
        query.with(Sort.by(Sort.Order.desc("score"))) // Sort by relevance
             .limit(10); // Return only top 10 results

        return mongoTemplate.find(query, UserEntity.class);
    }
}