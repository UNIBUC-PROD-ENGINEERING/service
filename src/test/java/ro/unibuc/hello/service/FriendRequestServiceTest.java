package ro.unibuc.hello.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ro.unibuc.hello.data.FriendRequestEntity;
import ro.unibuc.hello.data.FriendRequestRepository;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.dto.User;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FriendRequestServiceTest {

    @Mock
    private FriendRequestRepository friendRequestRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private FriendRequestService friendRequestService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendRequestSuccess() {
        when(friendRequestRepository.findByFromUserIdAndToUserId("u1", "u2")).thenReturn(Collections.emptyList());
        when(friendRequestRepository.findByFromUserIdAndToUserId("u2", "u1")).thenReturn(Collections.emptyList());

        FriendRequestEntity saved = new FriendRequestEntity("u1", "u2", "PENDING");
        when(friendRequestRepository.save(any())).thenReturn(saved);

        FriendRequestEntity result = friendRequestService.sendRequest("u1", "u2");

        assertEquals("u1", result.getFromUserId());
        assertEquals("u2", result.getToUserId());
        assertEquals("PENDING", result.getStatus());
    }

    @Test
    void testSendRequestToSelfThrows() {
        assertThrows(IllegalArgumentException.class, () -> {
            friendRequestService.sendRequest("u1", "u1");
        });
    }

    @Test
    void testSendRequestAlreadyExistsThrows() {
        when(friendRequestRepository.findByFromUserIdAndToUserId("u1", "u2"))
                .thenReturn(List.of(new FriendRequestEntity("u1", "u2", "PENDING")));

        assertThrows(IllegalArgumentException.class, () -> {
            friendRequestService.sendRequest("u1", "u2");
        });
    }

    @Test
    void testGetReceivedRequests() {
        List<FriendRequestEntity> requests = List.of(
                new FriendRequestEntity("u1", "u2", "PENDING")
        );
        when(friendRequestRepository.findByToUserIdAndStatus("u2", "PENDING")).thenReturn(requests);

        List<FriendRequestEntity> result = friendRequestService.getReceivedRequests("u2");

        assertEquals(1, result.size());
        assertEquals("u1", result.get(0).getFromUserId());
    }

    @Test
    void testRespondToRequestAccept() {
        FriendRequestEntity request = new FriendRequestEntity("u1", "u2", "PENDING");
        request.setId("req1");

        when(friendRequestRepository.findById("req1")).thenReturn(Optional.of(request));

        boolean result = friendRequestService.respondToRequest("req1", true);

        assertTrue(result);
        assertEquals("ACCEPTED", request.getStatus());
    }

    @Test
    void testRespondToRequestAlreadyHandled() {
        FriendRequestEntity request = new FriendRequestEntity("u1", "u2", "ACCEPTED");
        request.setId("req1");

        when(friendRequestRepository.findById("req1")).thenReturn(Optional.of(request));

        boolean result = friendRequestService.respondToRequest("req1", false);

        assertFalse(result);
    }

    @Test
    void testGetFriendsOfUserReturnsFriends() {
        UserEntity user = new UserEntity("test", "pass");
        user.setId("u1");

        when(userRepository.findById("u1")).thenReturn(Optional.of(user));
        when(friendRequestRepository.findByFromUserIdAndStatus("u1", "ACCEPTED"))
                .thenReturn(List.of(new FriendRequestEntity("u1", "u2", "ACCEPTED")));
        when(friendRequestRepository.findByToUserIdAndStatus("u1", "ACCEPTED"))
                .thenReturn(Collections.emptyList());

        UserEntity friend = new UserEntity("friend", "pass");
        friend.setId("u2");

        when(userRepository.findAllById(List.of("u2"))).thenReturn(List.of(friend));

        List<User> friends = friendRequestService.getFriendsOfUser("u1");

        assertEquals(1, friends.size());
        assertEquals("u2", friends.get(0).getId());
    }

    @Test
    void testGetFriendsOfUserNone() {
        when(userRepository.findById("u1")).thenReturn(Optional.of(new UserEntity()));

        when(friendRequestRepository.findByFromUserIdAndStatus("u1", "ACCEPTED")).thenReturn(Collections.emptyList());
        when(friendRequestRepository.findByToUserIdAndStatus("u1", "ACCEPTED")).thenReturn(Collections.emptyList());

        assertThrows(IllegalStateException.class, () -> {
            friendRequestService.getFriendsOfUser("u1");
        });
    }

    @Test
    void testRemoveFriendSuccess() {
        FriendRequestEntity request = new FriendRequestEntity("u1", "u2", "ACCEPTED");
        List<FriendRequestEntity> sent = List.of(request);
        when(friendRequestRepository.findByStatusAndFromUserIdAndToUserId("ACCEPTED", "u1", "u2"))
                .thenReturn(sent);
        when(friendRequestRepository.findByStatusAndFromUserIdAndToUserId("ACCEPTED", "u2", "u1"))
                .thenReturn(Collections.emptyList());

        boolean result = friendRequestService.removeFriend("u1", "u2");

        assertTrue(result);
        verify(friendRequestRepository).deleteAll(sent);
    }

    @Test
    void testRemoveFriendNotFound() {
        when(friendRequestRepository.findByStatusAndFromUserIdAndToUserId("ACCEPTED", "u1", "u2"))
                .thenReturn(Collections.emptyList());
        when(friendRequestRepository.findByStatusAndFromUserIdAndToUserId("ACCEPTED", "u2", "u1"))
                .thenReturn(Collections.emptyList());

        boolean result = friendRequestService.removeFriend("u1", "u2");

        assertFalse(result);
    }
    
}
