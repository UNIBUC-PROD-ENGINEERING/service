package ro.unibuc.hello.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import ro.unibuc.hello.config.JwtTestUtil;
import ro.unibuc.hello.config.NoSecurityConfig;
import ro.unibuc.hello.entity.Client;
import ro.unibuc.hello.entity.Group;
import ro.unibuc.hello.repository.ClientRepository;
import ro.unibuc.hello.repository.GroupRepository;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(NoSecurityConfig.class)
@Tag("IntegrationTest")
public class GroupControllerIntegrationTest {

    @Container
    static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0.20")
            .withCommand("--replSet", "rs0");

    static {
        mongoDBContainer.start();
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired private MockMvc mockMvc;
    @Autowired private GroupRepository groupRepository;
    @Autowired private ClientRepository clientRepository;

    private Client creator;
    private String token;

    @BeforeEach
    void setUp() {
        groupRepository.deleteAll();
        clientRepository.deleteAll();

        creator = new Client();
        creator.setEmail("user123@example.com");
        creator = clientRepository.save(creator);
        token = JwtTestUtil.generateTestToken(creator.getEmail());
    }

    @Test
    void testCreateGroup_success() throws Exception {
        String groupJson = """
            {
                "name": "Test Group",
                "clientIds": ["%s"],
                "transactionIds": [],
                "pendingInvites": []
            }
        """.formatted(creator.getId());

        mockMvc.perform(post("/groups")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(groupJson)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Group"))
                .andExpect(jsonPath("$.createdBy").value(creator.getId()));
    }

    @Test
    void testGetGroup_success() throws Exception {
        Group group = new Group();
        group.setName("Existing Group");
        group.setCreatedBy(creator.getId());
        group.setClientIds(List.of(creator.getId()));
        group.setTransactionIds(List.of());
        group.setPendingInvites(List.of());
        group = groupRepository.save(group);

        mockMvc.perform(get("/groups/" + group.getId())
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Existing Group"));
    }

    @Test
    void testDeleteGroup_success() throws Exception {
        Group group = new Group();
        group.setName("To Delete");
        group.setCreatedBy(creator.getId());
        group.setClientIds(List.of(creator.getId()));
        group.setTransactionIds(List.of());
        group.setPendingInvites(List.of());
        group = groupRepository.save(group);

        mockMvc.perform(delete("/groups/" + group.getId())
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string("Group deleted successfully"));
    }

    @Test
    void testInviteClient_success() throws Exception {
        Client invited = new Client();
        invited.setEmail("user456@example.com");
        invited = clientRepository.save(invited);

        Group group = new Group();
        group.setName("Group with invite");
        group.setCreatedBy(creator.getId());
        group.setClientIds(List.of(creator.getId()));
        group.setTransactionIds(List.of());
        group.setPendingInvites(List.of());
        group = groupRepository.save(group);

        mockMvc.perform(put("/groups/" + group.getId() + "/invite/" + invited.getId())
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string("Invitation sent to user " + invited.getId()));
    }

    @Test
    void testAcceptInvite_success() throws Exception {
        Client invited = new Client();
        invited.setEmail("user456@example.com");
        invited = clientRepository.save(invited);
        String invitedToken = JwtTestUtil.generateTestToken(invited.getEmail());

        Group group = new Group();
        group.setName("Group with invite");
        group.setCreatedBy(creator.getId());
        group.setClientIds(List.of(creator.getId()));
        group.setTransactionIds(List.of());
        group.setPendingInvites(List.of(invited.getId()));
        group = groupRepository.save(group);

        mockMvc.perform(put("/groups/" + group.getId() + "/accept")
                        .header("Authorization", "Bearer " + invitedToken))
                .andExpect(status().isOk())
                .andExpect(content().string("User joined the group"));
    }

    @Test
    void testRemoveClient_success() throws Exception {
        Client other = new Client();
        other.setEmail("user456@example.com");
        other = clientRepository.save(other);

        Group group = new Group();
        group.setName("Group with members");
        group.setCreatedBy(creator.getId());
        group.setClientIds(List.of(creator.getId(), other.getId()));
        group.setTransactionIds(List.of());
        group.setPendingInvites(List.of());
        group = groupRepository.save(group);

        mockMvc.perform(put("/groups/" + group.getId() + "/remove/" + other.getId())
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string("User removed from group"));
    }
    // @Test
    // void testAddTransactionToGroup_success() throws Exception {
    //     Client member = new Client();
    //     member.setEmail("member@example.com");
    //     member = clientRepository.save(member);
    //     String memberToken = JwtTestUtil.generateTestToken(member.getEmail());
    
    //     Group group = new Group();
    //     group.setName("Group with transaction");
    //     group.setCreatedBy(member.getId());
    //     group.setClientIds(List.of(member.getId()));
    //     group.setTransactionIds(List.of());
    //     group.setPendingInvites(List.of());
    //     group = groupRepository.save(group);
    
    //     String body = """
    //         {
    //             "transactionId": "txn789"
    //         }
    //     """;
    
    //     mockMvc.perform(post("/groups/" + group.getId() + "/transactions")
    //                     .contentType(MediaType.APPLICATION_JSON)
    //                     .content(body)
    //                     .header("Authorization", "Bearer " + memberToken))
    //             .andExpect(status().isOk())
    //             .andExpect(content().string("Transaction txn789 added to group " + group.getId()));
    // }
    
}
