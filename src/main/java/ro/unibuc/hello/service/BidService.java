package ro.unibuc.hello.service;

import java.util.List;
import java.util.stream.Collectors;

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
                .map(entity -> new Bid(entity))
                .collect(Collectors.toList());
    }

    public Bid getBidById(String id) {
        BidEntity entity = bidRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));

        return new Bid(entity);
    }

    public Bid saveBid(String bidderId, BidPost bid) {
        BidEntity entity = new BidEntity();
        entity.setPrice(bid.getPrice());

        AuctionEntity auction = auctionRepository.findById(bid.getAuctionId())
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(bid.getAuctionId())));
        entity.setAuction(auction);

        UserEntity bidder = userRepository.findById(bidderId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        entity.setBidder(bidder);

        bidRepository.save(entity);
        return new Bid(entity);
    }

    public List<Bid> saveAll(String bidderId, List<BidPost> bids) {
        List<BidEntity> entities = bids.stream()
                .map(bid -> {
                    BidEntity entity = new BidEntity();
                    entity.setPrice(bid.getPrice());

                    AuctionEntity auction = auctionRepository.findById(bid.getAuctionId())
                            .orElseThrow(() -> new EntityNotFoundException(String.valueOf(bid.getAuctionId())));
                    entity.setAuction(auction);
            
                    UserEntity bidder = userRepository.findById(bidderId)
                            .orElseThrow(() -> new EntityNotFoundException("User not found"));
                    entity.setBidder(bidder);

                    return entity;
                })
                .collect(Collectors.toList());

        List<BidEntity> savedEntities = bidRepository.saveAll(entities);

        return savedEntities.stream()
                .map(entity -> new Bid(entity))
                .collect(Collectors.toList());
    }

    public void deleteAllBids() {
        bidRepository.deleteAll();
    }
}
