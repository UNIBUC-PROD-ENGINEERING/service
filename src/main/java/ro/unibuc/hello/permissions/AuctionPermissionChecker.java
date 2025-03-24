package ro.unibuc.hello.permissions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.unibuc.hello.data.AuctionEntity;
import ro.unibuc.hello.data.AuctionRepository;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.exception.UnauthorizedException;

@Component
public class AuctionPermissionChecker implements PermissionChecker<AuctionEntity> {
    @Autowired
    public AuctionRepository auctionRepository;
    
    @Override
    public void checkOwnership(String userId, String resourceId) {
        AuctionEntity auction = auctionRepository.findById(resourceId)
            .orElseThrow(() -> new EntityNotFoundException("Auction not found"));

        if (!auction.getAuctioneer().getId().equals(userId)) {
            throw new UnauthorizedException("You do not own this auction");
        }
    }
}
