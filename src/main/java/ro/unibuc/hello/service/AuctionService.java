package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.SessionRepository;
import org.springframework.stereotype.Component;
import ro.unibuc.hello.data.AuctionEntity;
import ro.unibuc.hello.data.AuctionRepository;
import ro.unibuc.hello.data.BidEntity;
import ro.unibuc.hello.data.BidRepository;
import ro.unibuc.hello.data.ItemEntity;
import ro.unibuc.hello.data.ItemRepository;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.dto.Auction;
import ro.unibuc.hello.dto.AuctionPost;
import ro.unibuc.hello.exception.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AuctionService {

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private SessionService sessionService;

    public List<Auction> getAllAuctions() {
        List<AuctionEntity> entities = auctionRepository.findAll();
        return entities.stream()
                .map(entity -> new Auction( entity.getTitle(), entity.getDescription(), entity.getStartPrice(), entity.getItem(), entity.getHighestBid(), entity.getAuctioneer().getName()))
                .collect(Collectors.toList());
    }

    public Auction getAuctionById(String id) throws EntityNotFoundException {
        Optional<AuctionEntity> optionalEntity = auctionRepository.findById(id);
        AuctionEntity entity = optionalEntity.orElseThrow(() -> new EntityNotFoundException(id));
        return new Auction(  entity.getTitle(), entity.getDescription(), entity.getStartPrice(), entity.getItem(), entity.getHighestBid(), entity.getAuctioneer().getName());
    }

    public Auction saveAuction(AuctionPost auction) {
        AuctionEntity entity = new AuctionEntity();
        entity.setTitle(auction.getTitle());
        entity.setDescription(auction.getDescription());
        entity.setStartPrice(auction.getStartPrice());

        UserEntity user = userRepository.findByUsername(auction.getAuctioneerUsername());
        entity.setAuctioneer(user);

        ItemEntity item = itemRepository.findById(auction.getItem())
                          .orElseThrow(() -> new EntityNotFoundException("Item not found"));
        
        //limit creating auctions only for your items 
        // if(item.getOwner().getId() != this session user id)

        entity.setItem(item);

        BidEntity bid = bidRepository.findById(auction.getHighestBid())
                                 .orElseThrow(() -> new EntityNotFoundException("Item not found"));;
        entity.setHighestBid(bid);

        auctionRepository.save(entity);
        return new Auction(entity.getTitle(), entity.getDescription(), entity.getStartPrice(), entity.getItem(), entity.getHighestBid(), entity.getAuctioneer().getName());
    }

    public List<Auction> saveAll(List<AuctionPost> auctions) {
        List<AuctionEntity> entities = auctions.stream()
                .map(auction -> {
                    AuctionEntity entity = new AuctionEntity();
                    entity.setTitle(auction.getTitle());
                    entity.setDescription(auction.getDescription());
                    entity.setStartPrice(auction.getStartPrice());

                    UserEntity user = userRepository.findByUsername(auction.getAuctioneerUsername());
                    entity.setAuctioneer(user);

                    ItemEntity item = itemRepository.findById(auction.getItem())
                                     .orElseThrow(() -> new EntityNotFoundException("Item not found"));
                    entity.setItem(item);

                    BidEntity bid = bidRepository.findById(auction.getHighestBid())
                                    .orElseThrow(() -> new EntityNotFoundException("Item not found"));
                    entity.setHighestBid(bid);

                    return entity;
                })
                .collect(Collectors.toList());

        List<AuctionEntity> savedEntities = auctionRepository.saveAll(entities);

        return savedEntities.stream()
                .map(entity -> new Auction(  entity.getTitle(), entity.getDescription(), entity.getStartPrice(), entity.getItem(), entity.getHighestBid(), entity.getAuctioneer().getName()))
                .collect(Collectors.toList());
    }

    public Auction updateAuction(String id, Auction auction) throws EntityNotFoundException {
        AuctionEntity entity = auctionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
        entity.setDescription(auction.getDescription());
        auctionRepository.save(entity);
        return new Auction(  entity.getTitle(), entity.getDescription(), entity.getStartPrice(), entity.getItem(), entity.getHighestBid(), entity.getAuctioneer().getName());
    }

    public void deleteAuction(String id) throws EntityNotFoundException {
        AuctionEntity entity = auctionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
                auctionRepository.delete(entity);
    }

    public void deleteAllAuctions() {
        auctionRepository.deleteAll();
    }
}
