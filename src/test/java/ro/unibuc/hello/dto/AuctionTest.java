package ro.unibuc.hello.dto;

import static org.junit.Assert.assertSame;

import org.junit.jupiter.api.Test;

import ro.unibuc.hello.data.AuctionEntity;
import ro.unibuc.hello.data.ItemEntity;
import ro.unibuc.hello.data.UserEntity;

public class AuctionTest {

    Auction auction = new Auction("1", "Auction 1", "Description 1", 100, "open");
    Auction emptyAuction = new Auction();
    UserEntity user1 = new UserEntity("11", "user 1", "password1", "username1");
    ItemEntity item1 = new ItemEntity("21", "Item 1", "description 1", user1);
    Auction auctionFromEntity = new Auction(new AuctionEntity("1", "Auction 1", "Description 1", 100, true, item1, user1));
    Auction auctionFromEntity_closed = new Auction(new AuctionEntity("1", "Auction 1", "Description 1", 100, false, item1, user1));

    @Test
    void testGetId() {
        assertSame("1", auction.getId());
        assertSame("1", auctionFromEntity.getId());
    }

    @Test
    void testGetTitle() {
        assertSame("Auction 1", auction.getTitle());
        assertSame("Auction 1", auctionFromEntity.getTitle());
    }


    @Test
    void testGetDescription() {
        assertSame("Description 1", auction.getDescription());
        assertSame("Description 1", auctionFromEntity.getDescription());
    }

    @Test
    void testGetStartPrice() {
        assertSame(100, auction.getStartPrice());
        assertSame(100, auctionFromEntity.getStartPrice());
    }

    @Test
    void testGetStatus() {
        assertSame("open", auction.getStatus());
        assertSame("open", auctionFromEntity.getStatus());
        assertSame("closed", auctionFromEntity_closed.getStatus());
    }

    @Test
    void testSetId() {
        emptyAuction.setId("1");
        assertSame("1", emptyAuction.getId());
    }

    @Test
    void testSetTitle() {
        emptyAuction.setTitle("Auction 1");
        assertSame("Auction 1", emptyAuction.getTitle());
    }

    @Test
    void testSetDescription() {
        emptyAuction.setDescription("description 1");
        assertSame("description 1", emptyAuction.getDescription());
    }

    @Test
    void testSetStartPrice() {
        emptyAuction.setStartPrice(20);
        assertSame(20, emptyAuction.getStartPrice());
    }

    @Test
    void testSetStatus() {
        emptyAuction.setStatus("open");
        assertSame("open", emptyAuction.getStatus());
    }
}
