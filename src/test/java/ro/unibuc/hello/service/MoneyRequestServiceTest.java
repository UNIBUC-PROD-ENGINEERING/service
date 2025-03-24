package ro.unibuc.hello.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import ro.unibuc.hello.entity.BankAccount;
import ro.unibuc.hello.entity.MoneyRequest;
import ro.unibuc.hello.entity.Transaction;
import ro.unibuc.hello.repository.BankAccountRepository;
import ro.unibuc.hello.repository.MoneyRequestRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class MoneyRequestServiceTest {

    @Mock
    private MoneyRequestRepository moneyRequestRepository;

    @Mock
    private TransactionService transactionService;

    @Mock
    private NotificationService notificationService;

    @Mock
    private BankAccountRepository bankAccountRepository;

    @InjectMocks
    private MoneyRequestService moneyRequestService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        moneyRequestService = new MoneyRequestService(moneyRequestRepository);

        // manually inject other dependencies
        ReflectionTestUtils.setField(moneyRequestService, "bankAccountRepository", bankAccountRepository);
        ReflectionTestUtils.setField(moneyRequestService, "notificationService", notificationService);
        ReflectionTestUtils.setField(moneyRequestService, "transactionService", transactionService);
    }


    @Test
    void testGetAllRequests() {
        List<MoneyRequest> requests = List.of(new MoneyRequest(), new MoneyRequest());
        when(moneyRequestRepository.findAll()).thenReturn(requests);

        List<MoneyRequest> result = moneyRequestService.getAllRequests();
        assertEquals(2, result.size());
    }

    @Test
    void testGetRequestById() {
        MoneyRequest request = new MoneyRequest();
        request.setId("123");
        when(moneyRequestRepository.findById("123")).thenReturn(Optional.of(request));

        Optional<MoneyRequest> result = moneyRequestService.getRequestById("123");
        assertTrue(result.isPresent());
        assertEquals("123", result.get().getId());
    }

    @Test
    void testGetRequestsForUser() {
        List<MoneyRequest> list = List.of(new MoneyRequest());
        when(moneyRequestRepository.findByToAccountId("user1")).thenReturn(list);

        List<MoneyRequest> result = moneyRequestService.getRequestsForUser("user1");
        assertEquals(1, result.size());
    }

    @Test
    void testCreateRequest() {
        MoneyRequest req = new MoneyRequest();
        req.setStatus("SHOULD_CHANGE");

        MoneyRequest saved = new MoneyRequest();
        saved.setStatus("PENDING");

        when(moneyRequestRepository.save(any())).thenReturn(saved);

        MoneyRequest result = moneyRequestService.createRequest(req);
        assertEquals("PENDING", result.getStatus());
    }

    @Test
    void testUpdateRequestStatus_Approved() {
        MoneyRequest request = new MoneyRequest("1", "fromId", "toId", 50.0, "PENDING");
        BankAccount sender = new BankAccount("toId", "Sender Name", "IBAN1", 1000.0, "client1");
        BankAccount receiver = new BankAccount("fromId", "Receiver Name", "IBAN2", 500.0, "client2");
        
        Transaction transaction = new Transaction();

        when(moneyRequestRepository.findById("1")).thenReturn(Optional.of(request));
        when(bankAccountRepository.findById("toId")).thenReturn(Optional.of(sender));
        when(bankAccountRepository.findById("fromId")).thenReturn(Optional.of(receiver));
        when(transactionService.saveTransaction(any())).thenReturn(transaction);

        MoneyRequest result = moneyRequestService.updateRequestStatus("1", "APPROVED");
        assertEquals("APPROVED", result.getStatus());
        verify(notificationService, times(2)).createNotification(anyString(), anyString());
    }

    @Test
    void testUpdateRequestStatus_Declined() {
        MoneyRequest request = new MoneyRequest("1", "fromId", "toId", 50.0, "PENDING");
        BankAccount sender = new BankAccount("toId", "Sender Name", "IBAN1", 1000.0, "client1");
        BankAccount receiver = new BankAccount("fromId", "Receiver Name", "IBAN2", 500.0, "client2");


        when(moneyRequestRepository.findById("1")).thenReturn(Optional.of(request));
        when(bankAccountRepository.findById("toId")).thenReturn(Optional.of(sender));
        when(bankAccountRepository.findById("fromId")).thenReturn(Optional.of(receiver));

        MoneyRequest result = moneyRequestService.updateRequestStatus("1", "DECLINED");
        assertEquals("DECLINED", result.getStatus());
        verify(notificationService).createNotification(eq("client1"), contains("refuzat"));
    }

    @Test
    void testUpdateRequestStatus_AlreadyProcessed() {
        MoneyRequest request = new MoneyRequest("1", "fromId", "toId", 50.0, "APPROVED");
        when(moneyRequestRepository.findById("1")).thenReturn(Optional.of(request));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            moneyRequestService.updateRequestStatus("1", "DECLINED");
        });
        assertTrue(exception.getMessage().contains("already processed"));
    }

    @Test
    void testUpdateRequestStatus_RequestNotFound() {
        when(moneyRequestRepository.findById("1")).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            moneyRequestService.updateRequestStatus("1", "APPROVED");
        });
        assertTrue(exception.getMessage().contains("not found"));
    }

    @Test
    void testUpdateRequestStatus_BankAccountNotFound() {
        MoneyRequest request = new MoneyRequest("1", "fromId", "toId", 50.0, "PENDING");
        when(moneyRequestRepository.findById("1")).thenReturn(Optional.of(request));
        when(bankAccountRepository.findById("toId")).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            moneyRequestService.updateRequestStatus("1", "APPROVED");
        });
        assertTrue(exception.getMessage().contains("Bank account not found"));
    }

    @Test
    void testUpdateRequestStatus_ReceiverBankAccountNotFound() {
        MoneyRequest request = new MoneyRequest("1", "fromId", "toId", 50.0, "PENDING");

        BankAccount sender = new BankAccount("toId", "Sender", "IBAN1", 1000.0, "client1");

        when(moneyRequestRepository.findById("1")).thenReturn(Optional.of(request));
        when(bankAccountRepository.findById("toId")).thenReturn(Optional.of(sender)); // sender exists
        when(bankAccountRepository.findById("fromId")).thenReturn(Optional.empty());  // receiver missing

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            moneyRequestService.updateRequestStatus("1", "APPROVED");
        });

        assertTrue(exception.getMessage().contains("Bank account not found"));
    }

}
