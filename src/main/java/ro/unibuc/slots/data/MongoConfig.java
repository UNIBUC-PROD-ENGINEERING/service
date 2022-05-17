package ro.unibuc.slots.data;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

import java.util.Collection;
import java.util.Collections;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {
    @Value("${mongodb.connection.url}")
    private String connectionURL;

    @Override
    protected String getDatabaseName() {
        return "slots";
    }

    @Override
    public MongoClient mongoClient() {
        return MongoClients.create(MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionURL))
                .build()
        );
    }

    @Override
    public Collection<String> getMappingBasePackages() {
        return Collections.singleton("ro.unibuc.slots.data");
    }
}
