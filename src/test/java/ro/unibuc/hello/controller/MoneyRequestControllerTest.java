package ro.unibuc.hello.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.unibuc.hello.entity.MoneyRequest;
import ro.unibuc.hello.service.MoneyRequestService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MoneyRequestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MoneyRequestService moneyRequestService;

    @InjectMocks
    private MoneyRequestController moneyRequestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(moneyRequestController).build();
    }

    @Test
    void testGetAllRequests() throws Exception {
        MoneyRequest req1 = new MoneyRequest("1", "A", "B", 100.0, "PENDING");
        MoneyRequest req2 = new MoneyRequest("2", "C", "D", 200.0, "APPROVED");
        List<MoneyRequest> list = Arrays.asList(req1, req2);

        when(moneyRequestService.getAllRequests()).thenReturn(list);

        mockMvc.perform(get("/api/money-requests"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is("1")))
                .andExpect(jsonPath("$[1].id", is("2")));
    }

    @Test
    void testGetRequestById() throws Exception {
        MoneyRequest req = new MoneyRequest("1", "A", "B", 100.0, "PENDING");

        when(moneyRequestService.getRequestById("1")).thenReturn(Optional.of(req));

        mockMvc.perform(get("/api/money-requests/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("1")))
                .andExpect(jsonPath("$.status", is("PENDING")));
    }

    @Test
    void testGetRequestsForUser() throws Exception {
        MoneyRequest req = new MoneyRequest("1", "A", "B", 100.0, "PENDING");
        when(moneyRequestService.getRequestsForUser("B")).thenReturn(List.of(req));

        mockMvc.perform(get("/api/money-requests/user/B"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].toAccountId", is("B")));
    }

    @Test
    void testCreateRequest() throws Exception {
        MoneyRequest req = new MoneyRequest("1", "A", "B", 100.0, "PENDING");
        when(moneyRequestService.createRequest(any())).thenReturn(req);

        String json = "{" +
                "\"id\": \"1\"," +
                "\"fromAccountId\": \"A\"," +
                "\"toAccountId\": \"B\"," +
                "\"amount\": 100.0," +
                "\"status\": \"PENDING\"" +
                "}";

        mockMvc.perform(post("/api/money-requests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("1")))
                .andExpect(jsonPath("$.status", is("PENDING")));
    }

    @Test
    void testUpdateRequestStatusValid() throws Exception {
        MoneyRequest updated = new MoneyRequest("1", "A", "B", 100.0, "APPROVED");
        when(moneyRequestService.updateRequestStatus("1", "APPROVED")).thenReturn(updated);

        mockMvc.perform(put("/api/money-requests/1/status?status=APPROVED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("APPROVED")));
    }

    @Test
    void testUpdateRequestStatusInvalid() {
        assertThrows(IllegalArgumentException.class, () -> {
            moneyRequestController.updateRequestStatus("1", "INVALID");
        });
    }

    @Test
    void testUpdateRequestStatusDeclined() throws Exception {
        MoneyRequest updated = new MoneyRequest("1", "A", "B", 100.0, "DECLINED");
        when(moneyRequestService.updateRequestStatus("1", "DECLINED")).thenReturn(updated);

        mockMvc.perform(put("/api/money-requests/1/status?status=DECLINED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("DECLINED")));
    }

}
