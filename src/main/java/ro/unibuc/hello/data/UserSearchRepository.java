package ro.unibuc.hello.data;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.data.mongodb.core.MongoTemplate;
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

        Query query = TextQuery.queryText(textCriteria)
            .sortByScore()
            .limit(10);

        return mongoTemplate.find(query, UserEntity.class);
    }
}