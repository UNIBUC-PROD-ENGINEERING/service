package ro.unibuc.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ro.unibuc.hello.data.*;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = {
        InformationRepository.class,
        UserRepository.class,
        ItemRepository.class,
        AuctionRepository.class,
        BidRepository.class
})
public class HelloApplication {

    @Autowired
    private InformationRepository informationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private BidRepository bidRepository;

    public static void main(String[] args) {
        SpringApplication.run(HelloApplication.class, args);
    }

    @PostConstruct
    public void runAfterObjectCreated() {
        informationRepository.deleteAll();
        informationRepository.save(new InformationEntity("Overview",
                "This is an example of using a data storage engine running separately from our applications server"));

        userRepository.deleteAll();
        UserEntity user = new UserEntity("67d06c34d4a81b711f91b537", "nume", "parola", "username");
        user = userRepository.save(user);

        UserEntity user2 = new UserEntity("67d06c34d4a81b711f91b538","Ana", "parola", "ana123");
        user2 = userRepository.save(user2);

        itemRepository.deleteAll();
        ItemEntity item = new ItemEntity("nume entitate", "descriere");
        item.setOwner(user);
        item = itemRepository.save(item);
        
        ItemEntity item2 = new ItemEntity("nume 2", "descriere");
        item2.setOwner(user);
        item2 = itemRepository.save(item2);

        bidRepository.deleteAll();
        BidEntity bid1 = new BidEntity(1000, user.getId());
        bid1 = bidRepository.save(bid1);

        auctionRepository.deleteAll();
        AuctionEntity auction1 = new AuctionEntity("licitatie1 1", "O super licitatie", 1000);
        auction1.setAuctioneer(user);
        auction1.setItem(item2);
        auction1.setHighestBid(bid1);
        auction1 = auctionRepository.save(auction1);

        
    }

}