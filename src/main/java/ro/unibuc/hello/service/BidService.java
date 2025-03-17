package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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

    public List<Bid> getAllBids() {
        List<BidEntity> entities = bidRepository.findAll();
        return entities.stream()
                .map(entity -> {
                String userName = userRepository.findById(entity.getBidderId())
                        .map(UserEntity::getName) 
                        .orElse("Unknown User");
                return new Bid(entity.getPrice(), userName);
            })
                .collect(Collectors.toList());
    }

    public Bid getBidById(String id) throws EntityNotFoundException {
        Optional<BidEntity> optionalEntity = bidRepository.findById(id);
        BidEntity entity = optionalEntity.orElseThrow(() -> new EntityNotFoundException(id));

        String userName = userRepository.findById(entity.getBidderId())
                        .map(UserEntity::getName) 
                        .orElse("Unknown User");
        return new Bid(entity.getPrice(), userName);
    }

    public Bid saveBid(BidPost bid) {
        BidEntity entity = new BidEntity(bid.getPrice(), bid.getBidderId());
        bidRepository.save(entity);

        String userName = userRepository.findById(bid.getBidderId())
                        .map(UserEntity::getName) 
                        .orElse("Unknown User");

        return new Bid(entity.getPrice(), userName);
    }

    public List<Bid> saveAll(List<Bid> bids) {
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
                String userName = userRepository.findById(entity.getBidderId())
                        .map(UserEntity::getName) 
                        .orElse("Unknown User");
                return new Bid(entity.getPrice(), userName);
            })
                .collect(Collectors.toList());
    }

    public Bid updateBid(String id, Bid bid) throws EntityNotFoundException {
        BidEntity entity = bidRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
        entity.setPrice(bid.getPrice());
        bidRepository.save(entity);

        String userName = userRepository.findById(entity.getBidderId())
                        .map(UserEntity::getName) 
                        .orElse("Unknown User");

        return new Bid(bid.getPrice(), userName);
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
