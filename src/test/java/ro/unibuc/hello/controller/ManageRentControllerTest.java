package ro.unibuc.hello.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ro.unibuc.hello.data.Rent;
import ro.unibuc.hello.dto.LateRent;
import ro.unibuc.hello.service.ManageRentService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ManageRentControllerTest {

    @Mock
    private ManageRentService manageRentService;

    @InjectMocks
    private ManageRentController manageRentController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(manageRentController).build();
    }

    @Test
    void testGetAllActiveRents() throws Exception {
        Rent rent = new Rent("user1", "game1", 7);
        when(manageRentService.getAllActiveRents()).thenReturn(Arrays.asList(rent));

        mockMvc.perform(get("/manage/rented"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].userId").value("user1"))
                .andExpect(jsonPath("$[0].gameId").value("game1"));
    }

    @Test
    void testGetLateRenters() throws Exception {
        LateRent lateRent = new LateRent("user1", "game1", "2024-03-01 10:00");
        when(manageRentService.getLateRents()).thenReturn(Arrays.asList(lateRent));

        mockMvc.perform(get("/manage/late"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].userId").value("user1"))
                .andExpect(jsonPath("$[0].gameId").value("game1"));
    }
}