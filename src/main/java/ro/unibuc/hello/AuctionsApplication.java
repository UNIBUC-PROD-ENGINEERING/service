package ro.unibuc.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.SessionRepository;

import jakarta.annotation.PostConstruct;
import ro.unibuc.hello.data.AuctionEntity;
import ro.unibuc.hello.data.AuctionRepository;
import ro.unibuc.hello.data.BidEntity;
import ro.unibuc.hello.data.BidRepository;
import ro.unibuc.hello.data.ItemEntity;
import ro.unibuc.hello.data.ItemRepository;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.data.UserRepository;

@SpringBootApplication
@EnableScheduling
@EnableMongoRepositories(basePackageClasses = {
    UserRepository.class,
    ItemRepository.class,
    AuctionRepository.class,
    BidRepository.class,
    SessionRepository.class
})
public class AuctionsApplication {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private BidRepository bidRepository;

    public static void main(String[] args) {
        SpringApplication.run(AuctionsApplication.class, args);
    }

    @PostConstruct
    public void runAfterObjectCreated() {
        userRepository.deleteAll();
        UserEntity user = new UserEntity("67d06c34d4a81b711f91b537", "nume", "parola", "username");
        user = userRepository.save(user);

        UserEntity user2 = new UserEntity("67d06c34d4a81b711f91b538","Ana", "parola", "ana123");
        user2 = userRepository.save(user2);

        itemRepository.deleteAll();
        ItemEntity item = new ItemEntity("67d81d8a22dff66530467a47", "nume entitate", "descriere", user);
        item = itemRepository.save(item);
        
        ItemEntity item2 = new ItemEntity("67d81d8a22dff66530467a49", "nume 2", "descriere", user);
        item2 = itemRepository.save(item2);

        auctionRepository.deleteAll();
        AuctionEntity auction1 = new AuctionEntity("67e023dddffcbd0d847f6fd4", "licitatie1 1", "O super licitatie", 1000, true, item2, user);
        auction1 = auctionRepository.save(auction1);

        bidRepository.deleteAll();
        BidEntity bid1 = new BidEntity("67d81d8a22dff66530467a49", 1000, user, auction1);
        bid1 = bidRepository.save(bid1);

        auction1 = auctionRepository.save(auction1);

        // userRepository.deleteAll();
        // itemRepository.deleteAll();
        // auctionRepository.deleteAll();
        // bidRepository.deleteAll();

        // // Create users
        // UserEntity john = new UserEntity("1","John Doe", "password123", "john_doe");
        // john = userRepository.save(john);

        // UserEntity jane = new UserEntity("2", "Jane Smith", "securepass", "jane_smith");
        // jane = userRepository.save(jane);

        // UserEntity alex = new UserEntity("3","Alex Johnson", "alex12345", "alex_johnson");
        // alex = userRepository.save(alex);

        // UserEntity emily = new UserEntity("67d88af12661b2188d5ec4f7", "Emily Davis", "emily2024", "emily_davis");
        // emily = userRepository.save(emily);

        // // Create items
        // ItemEntity vintageWatch = new ItemEntity("Rolex Vintage Watch",
        //         "A rare Rolex watch from 1970 in excellent condition.");
        // vintageWatch.setOwner(john);
        // vintageWatch = itemRepository.save(vintageWatch);

        // ItemEntity painting = new ItemEntity("Van Gogh Painting", "An authentic Van Gogh painting from 1889.");
        // painting.setOwner(john);
        // painting = itemRepository.save(painting);

        // ItemEntity macbook = new ItemEntity("MacBook Pro 16-inch", "Latest model MacBook Pro with M3 chip.");
        // macbook.setOwner(alex);
        // macbook = itemRepository.save(macbook);

        // ItemEntity diamondRing = new ItemEntity("Diamond Engagement Ring", "A 2-carat diamond ring set in platinum.");
        // diamondRing.setOwner(emily);
        // diamondRing = itemRepository.save(diamondRing);

        // ItemEntity signedBook = new ItemEntity("Signed Book by J.K. Rowling",
        //         "A signed first edition of 'Harry Potter and the Sorcerer's Stone.'");
        // signedBook.setOwner(jane);
        // signedBook = itemRepository.save(signedBook);

        // // Create auctions
        // AuctionEntity auction1 = new AuctionEntity("Rolex Watch Auction",
        //         "Exclusive auction for a vintage Rolex watch.", 5000);
        // auction1.setAuctioneer(john);
        // auction1.setItem(vintageWatch);
        // auction1 = auctionRepository.save(auction1);

        // AuctionEntity auction2 = new AuctionEntity("Van Gogh Painting Auction",
        //         "A rare opportunity to own a Van Gogh masterpiece.", 1000000);
        // auction2.setAuctioneer(john);
        // auction2.setItem(painting);
        // auction2 = auctionRepository.save(auction2);

        // AuctionEntity auction3 = new AuctionEntity("MacBook Pro Auction",
        //         "Brand new MacBook Pro available at a starting price.", 2000);
        // auction3.setAuctioneer(alex);
        // auction3.setItem(macbook);
        // auction3 = auctionRepository.save(auction3);

        // AuctionEntity auction4 = new AuctionEntity("Diamond Ring Auction",
        //         "Bid for a stunning diamond engagement ring.", 10000);
        // auction4.setAuctioneer(emily);
        // auction4.setItem(diamondRing);
        // auction4 = auctionRepository.save(auction4);

        // AuctionEntity auction5 = new AuctionEntity("Signed Harry Potter Book Auction",
        //         "A signed copy of J.K. Rowling's first book.", 1500);
        // auction5.setAuctioneer(jane);
        // auction5.setItem(signedBook);
        // auction5 = auctionRepository.save(auction5);

        // // Create bids
        // BidEntity bid1 = new BidEntity(5200, jane, auction1);
        // bid1 = bidRepository.save(bid1);

        // BidEntity bid2 = new BidEntity(1050000, alex, auction2);
        // bid2 = bidRepository.save(bid2);

        // BidEntity bid3 = new BidEntity(2200, emily, auction3);
        // bid3 = bidRepository.save(bid3);

        // BidEntity bid4 = new BidEntity(11000, john, auction4);
        // bid4 = bidRepository.save(bid4);

        // BidEntity bid5 = new BidEntity(1800, alex, auction5);
        // bid5 = bidRepository.save(bid5);

        // // Set highest bids
        // auction1.setHighestBid(bid1);
        // auction1 = auctionRepository.save(auction1);

        // auction2.setHighestBid(bid2);
        // auction2 = auctionRepository.save(auction2);

        // auction3.setHighestBid(bid3);
        // auction3 = auctionRepository.save(auction3);

        // auction4.setHighestBid(bid4);
        // auction4 = auctionRepository.save(auction4);

        // auction5.setHighestBid(bid5);
        // auction5 = auctionRepository.save(auction5);
    }
}
