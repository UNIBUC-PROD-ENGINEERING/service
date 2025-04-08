package ro.unibuc.hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.data.FriendRequestRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@Tag("IntegrationTest")
public class FriendRequestControllerIntegrationTest {

    @Container
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0.20");

    @DynamicPropertySource
    static void mongoProps(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FriendRequestRepository friendRequestRepository;

    private String userId1, userId2;

    @BeforeEach
    void setUp() {
        friendRequestRepository.deleteAll();
        userRepository.deleteAll();

        UserEntity user1 = new UserEntity("andrei", "parola");
        UserEntity user2 = new UserEntity("matei", "parola2");

        userRepository.save(user1);
        userRepository.save(user2);

        userId1 = user1.getId();
        userId2 = user2.getId();
    }

    @Test
    public void testSendAndAcceptFriendRequest() throws Exception {
        mockMvc.perform(post("/friends/send")
                .param("fromUserId", userId1)
                .param("toUserId", userId2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fromUserId").value(userId1))
                .andExpect(jsonPath("$.toUserId").value(userId2))
                .andExpect(jsonPath("$.status").value("PENDING"));

        String requestId = friendRequestRepository
                .findByToUserIdAndStatus(userId2, "PENDING")
                .get(0).getId();

        mockMvc.perform(post("/friends/respond")
                .param("requestId", requestId)
                .param("accept", "true"))
                .andExpect(status().isOk())
                .andExpect(content().string("Friend request accepted."));

        mockMvc.perform(get("/friends/friends")
                .param("userId", userId1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(userId2));
    }

    @Test
    public void testRejectFriendRequest() throws Exception {
        mockMvc.perform(post("/friends/send")
                .param("fromUserId", userId2)
                .param("toUserId", userId1))
                .andExpect(status().isOk());

        String requestId = friendRequestRepository
                .findByToUserIdAndStatus(userId1, "PENDING")
                .get(0).getId();

        mockMvc.perform(post("/friends/respond")
                .param("requestId", requestId)
                .param("accept", "false"))
                .andExpect(status().isOk())
                .andExpect(content().string("Friend request rejected."));

        mockMvc.perform(get("/friends/friends")
                .param("userId", userId1))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("You don't have any friends yet."));
    }

    @Test
    public void testRemoveFriend() throws Exception {
        mockMvc.perform(post("/friends/send")
                .param("fromUserId", userId1)
                .param("toUserId", userId2))
                .andExpect(status().isOk());

        String requestId = friendRequestRepository
                .findByToUserIdAndStatus(userId2, "PENDING")
                .get(0).getId();

        mockMvc.perform(post("/friends/respond")
                .param("requestId", requestId)
                .param("accept", "true"))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/friends/remove")
                .param("userId1", userId1)
                .param("userId2", userId2))
                .andExpect(status().isOk())
                .andExpect(content().string("Friend removed successfully."));
    }

    @Test
    public void testCannotSendDuplicateOrSelfRequest() throws Exception {
        mockMvc.perform(post("/friends/send")
                .param("fromUserId", userId1)
                .param("toUserId", userId1))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("You cannot send a friend request to yourself."));

        mockMvc.perform(post("/friends/send")
                .param("fromUserId", userId1)
                .param("toUserId", userId2))
                .andExpect(status().isOk());

        mockMvc.perform(post("/friends/send")
                .param("fromUserId", userId1)
                .param("toUserId", userId2))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Friend request already exists or you are already friends."));
    }
}
