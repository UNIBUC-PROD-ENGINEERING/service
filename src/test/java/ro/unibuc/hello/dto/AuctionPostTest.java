package ro.unibuc.hello.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class AuctionPostTest {

    final AuctionPost auction = new AuctionPost("Title 1", "Description 1", 20, "21");
    AuctionPost auctionSet = new AuctionPost("Title 1", "Description 1", 20, "21");

    @Test
    void testGetTitle() {
        assertEquals("Title 1", auction.getTitle());
    }

    @Test
    void testGetDescription() {
        assertEquals("Description 1", auction.getDescription());
    }

    @Test
    void testGetStartPrice() {
        assertEquals(20, auction.getStartPrice());
    }

    @Test
    void testGetItemId() {
        assertEquals("21", auction.getItemId());
    }

    @Test
    void testSetTitle() {
        auctionSet.setTitle("set title");
        assertEquals("set title", auctionSet.getTitle());
    }

    @Test
    void testSetDescription() {
        auctionSet.setDescription("set description");
        assertEquals("set description", auctionSet.getDescription());
    }


    @Test
    void testSetStartPrice() {
        auctionSet.setStartPrice(10);
        assertEquals(10, auctionSet.getStartPrice());
    }

    @Test
    void testSetItemId() {
        auctionSet.setItemId("22");
        assertEquals("22", auctionSet.getItemId());
    }
}
