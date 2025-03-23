package ro.unibuc.hello.permissions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.unibuc.hello.data.BidEntity;
import ro.unibuc.hello.data.BidRepository;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.exception.UnauthorizedException;

@Component
public class BidPermissionChecker implements PermissionChecker<BidEntity> {
    @Autowired
    BidRepository bidRepository;

    @Override
    public void checkOwnership(String userId, String resourceId) {
        BidEntity bid = bidRepository.findById(resourceId)
            .orElseThrow(() -> new EntityNotFoundException("Bid not found"));

        if (!bid.getBidder().getId().equals(userId)) {
            throw new UnauthorizedException("You do not own this bid");
        }
    }
}
