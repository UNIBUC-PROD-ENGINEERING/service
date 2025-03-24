package ro.unibuc.hello.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import ro.unibuc.hello.entity.Client;
import ro.unibuc.hello.entity.Group;
import ro.unibuc.hello.entity.MoneyRequest;
import ro.unibuc.hello.repository.BankAccountRepository;
import ro.unibuc.hello.repository.ClientRepository;
import ro.unibuc.hello.repository.GroupRepository;
import ro.unibuc.hello.repository.TransactionRepository;
import ro.unibuc.hello.config.JwtUtil;
//import ro.unibuc.hello.service.MoneyRequestService;
import ro.unibuc.hello.entity.Transaction;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class GroupServiceTest {

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private MoneyRequestService moneyRequestService;

    @Mock
    private BankAccountRepository bankAccountRepository;

    @InjectMocks
    private GroupService groupService;

    @Test
    void testCreateGroup_ShouldCreateAndReturnGroup() {
        String token = "Bearer fake.token.jwt";
        String email = "test@email.com";
        String clientId = "client-123";

        Group group = new Group();
        group.setClientIds(null);
        group.setPendingInvites(null);
        group.setTransactionIds(null);

        Client client = new Client();
        client.setId(clientId);
        client.setEmail(email);

        when(jwtUtil.extractEmail("fake.token.jwt")).thenReturn(email);
        when(clientRepository.findByEmail(email)).thenReturn(Optional.of(client));
        when(groupRepository.save(any(Group.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Group result = groupService.createGroup(token, group);

        assertNotNull(result.getClientIds());
        assertTrue(result.getClientIds().contains(clientId));
        assertEquals(clientId, result.getCreatedBy());
        verify(groupRepository).save(group);
    }

    @Test
    void testInviteUser_Successful() {
        String token = "Bearer test.token";
        String email = "user@example.com";
        String clientId = "client-1";
        String groupId = "group-1";
        String invitedUserId = "client-2";

        Client client = new Client();
        client.setId(clientId);
        client.setEmail(email);

        Group group = new Group();
        group.setId(groupId);
        group.setCreatedBy(clientId);
        group.setClientIds(new ArrayList<>(List.of(clientId)));
        group.setPendingInvites(new ArrayList<>());

        when(jwtUtil.extractEmail("test.token")).thenReturn(email);
        when(clientRepository.findByEmail(email)).thenReturn(Optional.of(client));
        when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));

        groupService.inviteUser(token, groupId, invitedUserId);

        assertTrue(group.getPendingInvites().contains(invitedUserId));
        verify(groupRepository).save(group);
    }

    @Test
    void testAcceptInvite_ShouldAddClientToGroup() {
        String token = "Bearer token.jwt";
        String email = "accept@example.com";
        String clientId = "client-xyz";
        String groupId = "group-abc";

        Client client = new Client();
        client.setId(clientId);
        client.setEmail(email);

        Group group = new Group();
        group.setId(groupId);
        group.setPendingInvites(new ArrayList<>(List.of(clientId)));
        group.setClientIds(new ArrayList<>());

        when(jwtUtil.extractEmail("token.jwt")).thenReturn(email);
        when(clientRepository.findByEmail(email)).thenReturn(Optional.of(client));
        when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));

        groupService.acceptInvite(token, groupId);

        assertFalse(group.getPendingInvites().contains(clientId));
        assertTrue(group.getClientIds().contains(clientId));
        verify(groupRepository).save(group);
    }

    @Test
    void testGetGroup_ShouldReturnGroup_WhenExists() {
        String groupId = "g1";
        Group group = new Group();
        group.setId(groupId);

        when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));

        Group result = groupService.getGroup(groupId);

        assertEquals(groupId, result.getId());
    }

    @Test
    void testRemoveUser_Success_WhenCreatorRemovesMember() {
        String token = "Bearer valid.token";
        String email = "creator@example.com";
        String creatorId = "creator-123";
        String groupId = "group-1";
        String userIdToRemove = "user-123";

        Client creator = new Client();
        creator.setId(creatorId);
        creator.setEmail(email);

        Group group = new Group();
        group.setId(groupId);
        group.setCreatedBy(creatorId);
        group.setClientIds(new ArrayList<>(List.of(creatorId, userIdToRemove)));

        when(jwtUtil.extractEmail("valid.token")).thenReturn(email);
        when(clientRepository.findByEmail(email)).thenReturn(Optional.of(creator));
        when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));

        groupService.removeUser(token, groupId, userIdToRemove);

        assertFalse(group.getClientIds().contains(userIdToRemove));
        verify(groupRepository).save(group);
    }

    @Test
    void testRemoveUser_Failure_WhenNonCreatorTriesToRemove() {
        String token = "Bearer valid.token";
        String email = "noncreator@example.com";
        String nonCreatorId = "noncreator-123";
        String groupId = "group-1";
        String userIdToRemove = "user-123";

        Client nonCreator = new Client();
        nonCreator.setId(nonCreatorId);
        nonCreator.setEmail(email);

        Group group = new Group();
        group.setId(groupId);
        group.setCreatedBy("anotherId");
        group.setClientIds(new ArrayList<>(List.of(nonCreatorId, userIdToRemove)));

        when(jwtUtil.extractEmail("valid.token")).thenReturn(email);
        when(clientRepository.findByEmail(email)).thenReturn(Optional.of(nonCreator));
        when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));

        assertThrows(ResponseStatusException.class,
            () -> groupService.removeUser(token, groupId, userIdToRemove));
    }

    @Test
    void testDeleteGroup_Success_WhenCreatorDeletes() {
        String token = "Bearer valid.token";
        String email = "creator@example.com";
        String creatorId = "creator-123";
        String groupId = "group-1";

        Client creator = new Client();
        creator.setId(creatorId);
        creator.setEmail(email);

        Group group = new Group();
        group.setId(groupId);
        group.setCreatedBy(creatorId);

        when(jwtUtil.extractEmail("valid.token")).thenReturn(email);
        when(clientRepository.findByEmail(email)).thenReturn(Optional.of(creator));
        when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));

        groupService.deleteGroup(token, groupId);

        verify(groupRepository).deleteById(groupId);
    }
    @Test
    void testDeleteGroup_Failure_WhenNonCreatorTriesToDelete() {
        String token = "Bearer valid.token";
        String email = "noncreator@example.com";
        String nonCreatorId = "noncreator-123";
        String groupId = "group-1";

        Client nonCreator = new Client();
        nonCreator.setId(nonCreatorId);
        nonCreator.setEmail(email);

        Group group = new Group();
        group.setId(groupId);
        group.setCreatedBy("anotherId");

        when(jwtUtil.extractEmail("valid.token")).thenReturn(email);
        when(clientRepository.findByEmail(email)).thenReturn(Optional.of(nonCreator));
        when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));

        assertThrows(ResponseStatusException.class,
            () -> groupService.deleteGroup(token, groupId));
    }
 @Test
 void testAddTransactionToGroup_ShouldCreateMoneyRequestsForOtherMembers() {
     String token = "Bearer valid.token";
     String email = "payer@example.com";
     String payerId = "payer-123";
     String fromAccountId = "account-1";
     String transactionId = "txn-123";
     String groupId = "group-1";
 
     Client payer = new Client();
     payer.setId(payerId);
     payer.setEmail(email);
     payer.setBankAccountIds(List.of(fromAccountId));
 
     Client member1 = new Client();
     member1.setId("member-1");
     member1.setBankAccountIds(List.of("account-2"));
 
     Client member2 = new Client();
     member2.setId("member-2");
     member2.setBankAccountIds(List.of("account-3"));
 
     Transaction transaction = new Transaction();
     transaction.setId(transactionId);
     transaction.setAmount(90.0);
     transaction.setFromAccountId(fromAccountId);
     transaction.setToAccountId("account-999");
 
     Group group = new Group();
     group.setId(groupId);
     group.setCreatedBy(payerId);
     group.setClientIds(List.of(payerId, "member-1", "member-2"));
     group.setTransactionIds(new ArrayList<>());
 
     when(jwtUtil.extractEmail(anyString())).thenReturn(email);
     when(clientRepository.findByEmail(email)).thenReturn(Optional.of(payer));
     when(clientRepository.findById("member-1")).thenReturn(Optional.of(member1));
     when(clientRepository.findById("member-2")).thenReturn(Optional.of(member2));
     when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));
     when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(transaction));
     when(moneyRequestService.createRequest(any(MoneyRequest.class))).thenReturn(new MoneyRequest());
 
     groupService.addTransactionToGroup(token, groupId, transactionId);
 
     verify(moneyRequestService, times(2)).createRequest(any(MoneyRequest.class));
 }
 

    @Test
    void testAddTransactionToGroup_Failure_WhenNonMemberTriesToAddTransaction() {
        // Setup
        String token = "Bearer invalid.token";
        String email = "outsider@example.com";
        String outsiderId = "outsider-123";
        String groupId = "group-1";
        String transactionId = "txn-456";
        String fromAccountId = "acc-out";
    
        Client outsider = new Client();
        outsider.setId(outsiderId);
        outsider.setEmail(email);
        outsider.setBankAccountIds(List.of(fromAccountId));
    
        Transaction transaction = new Transaction();
        transaction.setId(transactionId);
        transaction.setAmount(100.0);
        transaction.setFromAccountId(fromAccountId);
        transaction.setToAccountId("acc-someone");
    
        Group group = new Group();
        group.setId(groupId);
        group.setCreatedBy("some-other-user");
        group.setClientIds(List.of("member-1", "member-2")); 
        group.setTransactionIds(new ArrayList<>());
    
        when(jwtUtil.extractEmail("invalid.token")).thenReturn(email);
        when(clientRepository.findByEmail(email)).thenReturn(Optional.of(outsider));
        when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));

        assertThrows(ResponseStatusException.class, () ->
            groupService.addTransactionToGroup(token, groupId, transactionId)
        );
    
        verify(moneyRequestService, never()).createRequest(any(MoneyRequest.class));
    }
    



}
