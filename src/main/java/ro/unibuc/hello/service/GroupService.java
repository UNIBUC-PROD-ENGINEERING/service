package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.entity.Group;
import ro.unibuc.hello.entity.Transaction;
import ro.unibuc.hello.repository.GroupRepository;
import ro.unibuc.hello.entity.MoneyRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import ro.unibuc.hello.entity.Group;
import ro.unibuc.hello.repository.GroupRepository;
import ro.unibuc.hello.repository.TransactionRepository;
import ro.unibuc.hello.repository.ClientRepository;
import ro.unibuc.hello.repository.BankAccountRepository;
import ro.unibuc.hello.service.MoneyRequestService;
import ro.unibuc.hello.config.JwtUtil;
import ro.unibuc.hello.entity.BankAccount;
import ro.unibuc.hello.entity.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class GroupService {

    
    private final GroupRepository groupRepository;
    private final ClientRepository clientRepository;
    private final JwtUtil jwtUtil;
    private final MoneyRequestService moneyRequestService;
    private final TransactionRepository transactionRepository;
    private final BankAccountRepository bankAccountRepository;
    

    @Autowired
    public GroupService(GroupRepository groupRepository, ClientRepository clientRepository, 
                    TransactionRepository transactionRepository, 
                    JwtUtil jwtUtil, MoneyRequestService moneyRequestService,
                    BankAccountRepository bankAccountRepository) {
    this.groupRepository = groupRepository;
    this.clientRepository = clientRepository;
    this.transactionRepository = transactionRepository;
    this.jwtUtil = jwtUtil;
    this.moneyRequestService = moneyRequestService;
    this.bankAccountRepository = bankAccountRepository;
}
   
    
    private Client getAuthenticatedClient(String token) {
        String jwt = token.startsWith("Bearer ") ? token.substring(7) : token;
        String email = jwtUtil.extractEmail(jwt);

        return clientRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found"));
    }

   public Group createGroup(String token, Group group) {
    Client client = getAuthenticatedClient(token);
    group.setCreatedBy(client.getId());

    if (group.getClientIds() == null) {
        group.setClientIds(new ArrayList<>());
    }
    if (group.getPendingInvites() == null) {
        group.setPendingInvites(new ArrayList<>());
    }
    if (group.getTransactionIds() == null) {
        group.setTransactionIds(new ArrayList<>());
    }

    group.getClientIds().add(client.getId()); 

    return groupRepository.save(group);
}


    public void inviteUser(String token, String groupId, String userId) {
        Client client = getAuthenticatedClient(token);
        Group group = getGroup(groupId);

        if (!group.getClientIds().contains(client.getId()) && !group.getCreatedBy().equals(client.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only members or the creator can invite users");
        }

        if (group.getClientIds().contains(userId) || group.getPendingInvites().contains(userId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is already in the group or invited");
        }

        group.getPendingInvites().add(userId);
        groupRepository.save(group);
    }

    public void acceptInvite(String token, String groupId) {
        Client client = getAuthenticatedClient(token);
        Group group = getGroup(groupId);

        if (!group.getPendingInvites().contains(client.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No pending invite found for user");
        }

        group.getPendingInvites().remove(client.getId());
        group.getClientIds().add(client.getId());
        groupRepository.save(group);
    }

    public void removeUser(String token, String groupId, String userId) {
        Client client = getAuthenticatedClient(token);
        Group group = getGroup(groupId);

        if (!group.getCreatedBy().equals(client.getId()) && !client.getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only the creator can remove others from the group");
        }

        if (!group.getClientIds().contains(userId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not in the group");
        }

        group.getClientIds().remove(userId);
        groupRepository.save(group);
    }

    public void deleteGroup(String token, String groupId) {
        Client client = getAuthenticatedClient(token);
        Group group = getGroup(groupId);

        if (!group.getCreatedBy().equals(client.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only the creator can delete the group");
        }

        groupRepository.deleteById(groupId);
    }

    public Group getGroup(String id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Group not found"));
    }
    public void addTransactionToGroup(String token, String groupId, String transactionId) {
        Client payer = getAuthenticatedClient(token);
        Group group = getGroup(groupId);
    
        if (!group.getClientIds().contains(payer.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only group members can add transactions");
        }
    
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found"));
    
        if (!group.getTransactionIds().contains(transactionId)) {
            group.getTransactionIds().add(transactionId);
            groupRepository.save(group);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transaction already added to the group");
        }
    
        splitTransaction(group, transaction.getFromAccountId(), transaction.getAmount());
    }
    
    private void splitTransaction(Group group, String payerBankAccount, double amount) {
        List<String> members = group.getClientIds();
    
        if (members == null || members.size() <= 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "At least two members needed to split a bill");
        }
    
        if (amount <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transaction amount must be greater than zero");
        }
    
        int numMembers = members.size() ; 
        double splitAmount = amount / numMembers;
    
        System.out.println("Splitting transaction of " + amount + " between " + numMembers + " members. Each pays: " + splitAmount);
    
        for (String memberId : members) {
            if (!memberId.equals(payerBankAccount)) { 
                try {
                    Client member = clientRepository.findById(memberId)
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found"));
    
                    if (member.getBankAccountIds().isEmpty()) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Member " + memberId + " has no bank account");
                    }
    
                    
                    String memberBankAccount = member.getBankAccountIds().get(0);
    
                    MoneyRequest request = new MoneyRequest();
                    request.setFromAccountId(memberBankAccount); 
                    request.setToAccountId(payerBankAccount); 
                    request.setAmount(splitAmount);
                    request.setStatus("PENDING");
    
                    moneyRequestService.createRequest(request); 
    
                    System.out.println("Created money request from " + memberBankAccount + " to " + payerBankAccount + " for amount: " + splitAmount);
                } catch (Exception e) {
                    System.err.println("Error creating money request for member " + memberId + ": " + e.getMessage());
                }
            }
        }
    }
    

    
}
