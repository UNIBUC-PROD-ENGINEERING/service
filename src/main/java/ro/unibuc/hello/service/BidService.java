package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.data.BidEntity;
import ro.unibuc.hello.data.BidRepository;
import ro.unibuc.hello.data.ItemEntity;
import ro.unibuc.hello.data.ItemRepository;
import ro.unibuc.hello.dto.Bid;
import ro.unibuc.hello.exception.BidException;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class BidService {

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private ItemRepository itemRepository;

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
    );

    public List<Bid> getAllBids() {
        List<BidEntity> bids = bidRepository.findAll();
        return bids.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<Bid> getBidsByItem(String itemId) {
        List<BidEntity> bids = bidRepository.findByItemId(itemId);
        return bids.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<Bid> getBidsByBidder(String bidderName) {
        List<BidEntity> bids = bidRepository.findByBidderName(bidderName);
        return bids.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Bid getBidById(String id) {
        BidEntity bid = bidRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
        return convertToDto(bid);
    }

    public Bid placeBid(Bid bidDto) {
        // Validate item exists
        ItemEntity item = itemRepository.findById(bidDto.getItemId())
                .orElseThrow(BidException::itemNotFound);

        // Check if item is active
        if (!item.isActive()) {
            throw BidException.itemNotActive();
        }

        // Check if bidding has ended
        if (item.getEndTime().isBefore(LocalDateTime.now())) {
            throw BidException.itemExpired();
        }

        // Validate email format
        if (!EMAIL_PATTERN.matcher(bidDto.getEmail()).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }

        // Check if bid amount is valid
        double minimumBid = item.getInitialPrice();
        List<BidEntity> existingBids = bidRepository.findByItemIdOrderByAmountDesc(item.getId());
        if (!existingBids.isEmpty()) {
            minimumBid = existingBids.get(0).getAmount();
        }

        if (bidDto.getAmount() <= minimumBid) {
            throw BidException.bidTooLow();
        }

        // Modified: Check if the bid is higher than the last one from the same user email
        // for the same auction (instead of using bidderName)
        List<BidEntity> userBids = bidRepository.findByItemIdAndEmailOrderByAmountDesc(item.getId(), bidDto.getEmail());
        if (!userBids.isEmpty()) {
            double lastUserBidAmount = userBids.get(0).getAmount();
            if (bidDto.getAmount() <= lastUserBidAmount) {
                throw new IllegalArgumentException("Bid amount must be higher than your last bid");
            }
        }

        // Save the bid
        BidEntity bid = new BidEntity(
                bidDto.getItemId(),
                bidDto.getBidderName(),
                bidDto.getAmount(),
                bidDto.getEmail()
        );

        BidEntity savedBid = bidRepository.save(bid);
        return convertToDto(savedBid);
    }

    public void deleteBid(String id) {
        BidEntity bid = bidRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
        bidRepository.delete(bid);
    }

    private Bid convertToDto(BidEntity bidEntity) {
        Bid bidDto = new Bid(
                bidEntity.getId(),
                bidEntity.getItemId(),
                bidEntity.getBidderName(),
                bidEntity.getAmount(),
                bidEntity.getCreatedAt(),
                bidEntity.getEmail()
        );

        // Add item name if available
        itemRepository.findById(bidEntity.getItemId()).ifPresent(item -> {
            bidDto.setItemName(item.getName());
        });

        return bidDto;
    }

    public List<Bid> getBidsByEmail(String email) {
        // Validate email format
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }

        List<BidEntity> bids = bidRepository.findByEmail(email);
        return bids.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}