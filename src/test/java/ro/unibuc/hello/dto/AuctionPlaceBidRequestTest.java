package ro.unibuc.hello.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class AuctionPlaceBidRequestTest {

    AuctionPlaceBidRequest request = new AuctionPlaceBidRequest(20);
    AuctionPlaceBidRequest emptyRequest = new AuctionPlaceBidRequest();

    @Test
    void testGetPrice() {
        assertEquals(20, request.getPrice());
    }

    @Test
    void testSetPrice() {
        emptyRequest.setPrice(10);
        assertEquals(10, emptyRequest.getPrice());
    }
}
