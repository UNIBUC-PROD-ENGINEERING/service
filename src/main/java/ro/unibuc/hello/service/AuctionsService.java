package ro.unibuc.hello.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
import ro.unibuc.hello.dto.AuctionDetails;
import ro.unibuc.hello.dto.AuctionPlaceBidRequest;
import ro.unibuc.hello.dto.AuctionPost;
import ro.unibuc.hello.dto.Bid;
import ro.unibuc.hello.exception.EntityNotFoundException;

@Component
public class AuctionsService {

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    public List<Auction> getAllAuctions() {
        List<AuctionEntity> entities = auctionRepository.findAll();
        return entities.stream()
            .map(entity -> new Auction(entity))
            .collect(Collectors.toList());
    }

    public AuctionDetails getAuctionById(String id) {
        AuctionEntity entity = auctionRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Auction not found"));

        Bid highestBid = getAuctionHighestBid(entity).orElse(null);
        List<Bid> bids = getAuctionBids(entity);
        return new AuctionDetails(entity, highestBid, bids);
    }

    public Auction saveAuction(String auctioneerId, AuctionPost auction) {
        AuctionEntity entity = new AuctionEntity();
        entity.setTitle(auction.getTitle());
        entity.setDescription(auction.getDescription());
        entity.setStartPrice(auction.getStartPrice());

        UserEntity user = userRepository.findById(auctioneerId)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));
        entity.setAuctioneer(user);

        ItemEntity item = itemRepository.findById(auction.getItemId())
            .orElseThrow(() -> new EntityNotFoundException("Item not found"));
        entity.setItem(item);

        entity = auctionRepository.save(entity);
        return new Auction(entity);
    }

    public List<Auction> saveAll(String auctioneerId, List<AuctionPost> auctions) {
        List<AuctionEntity> entities = auctions.stream()
            .map(auction -> {
                AuctionEntity entity = new AuctionEntity();
                entity.setTitle(auction.getTitle());
                entity.setDescription(auction.getDescription());
                entity.setStartPrice(auction.getStartPrice());

                UserEntity user = userRepository.findById(auctioneerId)
                        .orElseThrow(() -> new EntityNotFoundException("User not found"));
                entity.setAuctioneer(user);

                ItemEntity item = itemRepository.findById(auction.getItemId())
                        .orElseThrow(() -> new EntityNotFoundException("Item not found"));
                entity.setItem(item);

                return entity;
            })
            .collect(Collectors.toList());

        List<AuctionEntity> savedEntities = auctionRepository.saveAll(entities);

        return savedEntities.stream()
            .map(entity -> new Auction(entity))
            .collect(Collectors.toList());
    }

    public Auction updateAuction(String id, Auction auction) {
        AuctionEntity entity = auctionRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Auction not found"));

        entity.setTitle(auction.getTitle());
        entity.setDescription(auction.getDescription());
        entity = auctionRepository.save(entity);
        return new Auction(entity);
    }

    public Bid placeBid(String id, String userId, AuctionPlaceBidRequest bid) {
        AuctionEntity auction = auctionRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Auction not found"));

        UserEntity user = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // FIXME: bid checks

        BidEntity bidEntity = new BidEntity(bid.getPrice(), user, auction);
        bidEntity = bidRepository.save(bidEntity);
        return new Bid(bidEntity);
    }

    public void deleteAuction(String id) {
        AuctionEntity entity = auctionRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Auction not found"));
        auctionRepository.delete(entity);
    }

    public void deleteAllAuctions() {
        auctionRepository.deleteAll();
    }

    private List<Bid> getAuctionBids(AuctionEntity auction) {
        return bidRepository.findByAuction(auction).stream()
            .map(bidEntity -> new Bid(bidEntity))
            .collect(Collectors.toList());
    }

    private Optional<Bid> getAuctionHighestBid(AuctionEntity auction) {
        return bidRepository.findByAuction(auction).stream()
            .max(Comparator.comparing(BidEntity::getPrice))
            .map(bidEntity -> new Bid(bidEntity));
    }
}
