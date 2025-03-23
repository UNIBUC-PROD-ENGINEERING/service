package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.unibuc.hello.data.AuctionEntity;
import ro.unibuc.hello.data.AuctionRepository;
import ro.unibuc.hello.data.BidEntity;
import ro.unibuc.hello.data.BidRepository;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.dto.Bid;
import ro.unibuc.hello.dto.BidPost;
import ro.unibuc.hello.exception.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class BidService {

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuctionRepository auctionRepository;

    public List<Bid> getAllBids() {
        List<BidEntity> entities = bidRepository.findAll();
        return entities.stream()
                .map(entity -> {
                String userName = userRepository.findById(entity.getBidder().getId())
                        .map(UserEntity::getUsername) 
                        .orElse("Unknown User");

                String auctionName = auctionRepository.findById(entity.getAuction().getId())
                        .map(AuctionEntity::getTitle) 
                        .orElse("Unknown Auction");
                return new Bid(entity.getPrice(), userName, auctionName);
            })
                .collect(Collectors.toList());
    }

    public Bid getBidById(String id) throws EntityNotFoundException {
        Optional<BidEntity> optionalEntity = bidRepository.findById(id);
        BidEntity entity = optionalEntity.orElseThrow(() -> new EntityNotFoundException(id));

        String userName = userRepository.findById(entity.getBidder().getId())
                        .map(UserEntity::getUsername) 
                        .orElse("Unknown User");

        
        String auctionName = auctionRepository.findById(entity.getAuction().getId())
                        .map(AuctionEntity::getTitle) 
                        .orElse("Unknown Auction");    

        return new Bid(entity.getPrice(), userName, auctionName);
    }

    public Bid saveBid(BidPost bid) {

        UserEntity bidder = userRepository.findById(bid.getBidderId())
                            .orElseThrow(() -> new EntityNotFoundException(String.valueOf(bid.getBidderId())));

        AuctionEntity auction = auctionRepository.findById(bid.getAuctionId())
                            .orElseThrow(() -> new EntityNotFoundException(String.valueOf(bid.getAuctionId())));

        BidEntity entity = new BidEntity(bid.getPrice(), bidder, auction);

        bidRepository.save(entity);

        return new Bid(entity.getPrice(), bidder.getUsername(), auction.getTitle());
    }

    public List<Bid> saveAll(List<BidPost> bids) {
        List<BidEntity> entities = bids.stream()
                .map(bid -> {
                    BidEntity entity = new BidEntity();
                    entity.setPrice(bid.getPrice());
                    return entity;
                })
                .collect(Collectors.toList());

        List<BidEntity> savedEntities = bidRepository.saveAll(entities);

        return savedEntities.stream()
                .map(entity ->  {
                String userName = userRepository.findById(entity.getBidder().getId())
                        .map(UserEntity::getUsername) 
                        .orElse("Unknown User");
                
                String auctionName = auctionRepository.findById(entity.getAuction().getId())
                        .map(AuctionEntity::getTitle) 
                        .orElse("Unknown Auction");
                return new Bid(entity.getPrice(), userName, auctionName);
            })
                .collect(Collectors.toList());
    }

    public Bid updateBid(String id, BidPost bid) throws EntityNotFoundException {

        BidEntity entity = bidRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));

        entity.setPrice(bid.getPrice());

        UserEntity user = userRepository.findByUsername(bid.getBidderId());
        entity.setBidder(user);
        
        bidRepository.save(entity);

        String auctionName = auctionRepository.findById(entity.getAuction().getId())
                        .map(AuctionEntity::getTitle) 
                        .orElse("Unknown Auction");

        return new Bid(bid.getPrice(), user.getUsername(), auctionName);
    }

    public void deleteBid(String id) throws EntityNotFoundException {
        BidEntity entity = bidRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
                bidRepository.delete(entity);
    }

    public void deleteAllBids() {
        bidRepository.deleteAll();
    }
}
