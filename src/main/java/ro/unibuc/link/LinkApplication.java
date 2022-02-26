package ro.unibuc.link;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ro.unibuc.link.data.UrlEntity;
import ro.unibuc.link.data.UrlRepository;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = UrlRepository.class)
public class LinkApplication {

    @Autowired
    private UrlRepository urlRepository;

    public static void main(String[] args) {
        SpringApplication.run(LinkApplication.class, args);
    }

    @PostConstruct
    public void runAfterObjectCreated() {
        urlRepository.deleteAll();
        urlRepository.save(new UrlEntity("internal", "https://www.google.com", null));
    }

}
