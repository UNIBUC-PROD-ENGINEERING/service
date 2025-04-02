package ro.unibuc.hello.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.unibuc.hello.data.FriendRequestEntity;
import ro.unibuc.hello.dto.User;
import ro.unibuc.hello.service.FriendRequestService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class FriendRequestControllerTest {

    @Mock
    private FriendRequestService friendRequestService;

    @InjectMocks
    private FriendRequestController friendRequestController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(friendRequestController).build();
    }

    @Test
    void testSendFriendRequestSuccess() {
        // Arrange
        FriendRequestEntity request = new FriendRequestEntity("user1", "user2", "PENDING");
        when(friendRequestService.sendRequest("user1", "user2")).thenReturn(request);

        // Act
        ResponseEntity<?> response = friendRequestController.sendRequest("user1", "user2");

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(request, response.getBody());
    }

    @Test
    void testSendFriendRequestToSelf() {
        // Arrange
        when(friendRequestService.sendRequest("user1", "user1")).thenThrow(new IllegalArgumentException("You cannot send a friend request to yourself."));

        // Act
        ResponseEntity<?> response = friendRequestController.sendRequest("user1", "user1");

        // Assert
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("You cannot send a friend request to yourself.", response.getBody());
    }

    @Test
    void testGetReceivedRequests() {
        // Arrange
        List<FriendRequestEntity> requests = Arrays.asList(
                new FriendRequestEntity("user1", "user2", "PENDING"),
                new FriendRequestEntity("user3", "user2", "PENDING")
        );
        when(friendRequestService.getReceivedRequests("user2")).thenReturn(requests);

        // Act
        List<FriendRequestEntity> result = friendRequestController.getRequests("user2");

        // Assert
        assertEquals(2, result.size());
        assertEquals("user1", result.get(0).getFromUserId());
        assertEquals("user3", result.get(1).getFromUserId());
    }

    @Test
    void testRespondToRequestAccepted() {
        when(friendRequestService.respondToRequest("req1", true)).thenReturn(true);

        String result = friendRequestController.respond("req1", true);

        assertEquals("Friend request accepted.", result);
    }

    @Test
    void testRespondToRequestAlreadyHandled() {
        when(friendRequestService.respondToRequest("req1", false)).thenReturn(false);

        String result = friendRequestController.respond("req1", false);

        assertEquals("Friend request cannot be modified (maybe already responded or not found).", result);
    }

    @Test
    void testGetFriendsSuccess() {
        List<User> users = Collections.singletonList(new User("id", "user", "pass", 0, null));
        when(friendRequestService.getFriendsOfUser("user1")).thenReturn(users);

        ResponseEntity<?> response = friendRequestController.getFriends("user1");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(users, response.getBody());
    }

    @Test
    void testGetFriendsWhenNone() {
        when(friendRequestService.getFriendsOfUser("user1")).thenThrow(new IllegalStateException("You don't have any friends yet."));

        ResponseEntity<?> response = friendRequestController.getFriends("user1");

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("You don't have any friends yet.", response.getBody());
    }

    @Test
    void testRemoveFriendSuccess() {
        when(friendRequestService.removeFriend("user1", "user2")).thenReturn(true);

        ResponseEntity<String> response = friendRequestController.removeFriend("user1", "user2");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Friend removed successfully.", response.getBody());
    }

    @Test
    void testRemoveFriendNotFound() {
        when(friendRequestService.removeFriend("user1", "user2")).thenReturn(false);

        ResponseEntity<String> response = friendRequestController.removeFriend("user1", "user2");

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("No friendship found between users.", response.getBody());
    }
}
