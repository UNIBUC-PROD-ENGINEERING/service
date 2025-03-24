package ro.unibuc.hello.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class AuctionPutTest {

    final AuctionPut auction = new AuctionPut("Title 1", "Description 1");
    AuctionPut auctionSet = new AuctionPut("Title 1", "Description 1");

    @Test
    void testGetTitle() {
        assertEquals("Title 1", auction.getTitle());
    }

    @Test
    void testGetDescription() {
        assertEquals("Description 1", auction.getDescription());
    }

    @Test
    void testSetTitle() {
        auctionSet.setTitle("updated title");
        assertEquals("updated title", auctionSet.getTitle());
    }

    @Test
    void testSetDescription() {
        auctionSet.setDescription("updated description");
        assertEquals("updated description", auctionSet.getDescription());
    }
}
