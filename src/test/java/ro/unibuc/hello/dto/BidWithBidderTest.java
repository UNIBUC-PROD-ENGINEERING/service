package ro.unibuc.hello.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ro.unibuc.hello.data.AuctionEntity;
import ro.unibuc.hello.data.BidEntity;
import ro.unibuc.hello.data.ItemEntity;
import ro.unibuc.hello.data.UserEntity;

public class BidWithBidderTest {

    User user = new User("11", "user 1");
    BidWithBidder bid = new BidWithBidder("31", 100, user);

    UserEntity user1 = new UserEntity("11", "user 1", "username1", "password1");
    ItemEntity item1 = new ItemEntity("21", "Item 1", "description 1", user1);
    AuctionEntity auction = new AuctionEntity("1", "Auction 1", "Description 1", 100, true, item1, user1);
    BidEntity bidEntity = new BidEntity("31", 100, user1, auction);
    BidWithBidder bidFromEntity = new BidWithBidder(bidEntity);

    BidWithBidder emptyBid = new BidWithBidder();

    @Test
    void testGetId() {
        assertEquals("31", bid.getId());
        assertEquals("31", bidFromEntity.getId());
    }

    @Test
    void testGetPrice() {
        assertEquals(100, bid.getPrice());
        assertEquals(100, bidFromEntity.getPrice());
    }

    @Test
    void testGetBidder() {
        assertEquals("11", bid.getBidder().getId());
        assertEquals("user 1", bid.getBidder().getName());
        assertEquals("11", bidFromEntity.getBidder().getId());
        assertEquals("user 1", bidFromEntity.getBidder().getName());
    }

    @Test
    void testSetId() {
        emptyBid.setId("32");
        assertEquals("32", emptyBid.getId());
    }

    @Test
    void testSetPrice() {
        emptyBid.setPrice(10);
        assertEquals(10, emptyBid.getPrice());
    }

    @Test
    void testSetBidder() {
        User setUser = new User("12", "user 2");
        emptyBid.setBidder(setUser);
        assertEquals("12", emptyBid.getBidder().getId());
        assertEquals("user 2", emptyBid.getBidder().getName());
    }
}
