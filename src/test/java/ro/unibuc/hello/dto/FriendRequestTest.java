package ro.unibuc.hello.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FriendRequestTest {

    @Test
    void testConstructorAndGetters() {
        FriendRequest req = new FriendRequest("id123", "u1", "u2", "PENDING");

        assertEquals("id123", req.getId());
        assertEquals("u1", req.getFromUserId());
        assertEquals("u2", req.getToUserId());
        assertEquals("PENDING", req.getStatus());
    }

    @Test
    void testSetters() {
        FriendRequest req = new FriendRequest();
        req.setId("id1");
        req.setFromUserId("u1");
        req.setToUserId("u2");
        req.setStatus("ACCEPTED");

        assertEquals("id1", req.getId());
        assertEquals("u1", req.getFromUserId());
        assertEquals("u2", req.getToUserId());
        assertEquals("ACCEPTED", req.getStatus());
    }
    
}
