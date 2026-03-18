package ro.unibuc.prodeng.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import ro.unibuc.prodeng.exception.DuplicateMasinaException;
import ro.unibuc.prodeng.repository.UserRepository;
import ro.unibuc.prodeng.repository.MasinaRepository;
import ro.unibuc.prodeng.repository.TodoRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import ro.unibuc.prodeng.request.MasinaRequest;
import ro.unibuc.prodeng.response.MasinaResponse;
import ro.unibuc.prodeng.service.MasinaService;
import ro.unibuc.prodeng.service.TodoService;
import ro.unibuc.prodeng.service.UserService;

@WebMvcTest(MasinaController.class)
public class MasinaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MasinaService masinaService;

    @MockBean
    private UserService userService;

    @MockBean
    private TodoService todoService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private MasinaRepository masinaRepository;

    @MockBean
    private TodoRepository todoRepository;

    @MockBean
    private MongoTemplate mongoTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateMasinaSuccess() throws Exception {
        MasinaRequest request = new MasinaRequest("Dacia", "Logan", 2020, 10000.0, 100, "Benzina", 90, "a@b.ro");
        MasinaResponse response = new MasinaResponse("1", "Dacia", "Logan", 2020, 10000.0, 100, "Benzina", 90, ro.unibuc.prodeng.model.MasinaStatus.DISPONIBIL, "a@b.ro", false);

        when(masinaService.createMasina(any(MasinaRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/masini")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.marca").value("Dacia"));
    }

    @Test
    void testCreateMasinaValidationFailedByService() throws Exception {
        MasinaRequest request = new MasinaRequest("Dacia", "Logan", 2025 + 5, -100.0, 100, "Benzina", 90, "a@b.ro"); // An in viitor si pret negativ

        when(masinaService.createMasina(any(MasinaRequest.class)))
                .thenThrow(new IllegalArgumentException("Pretul trebuie sa fie strict mai mare decat 0"));

        mockMvc.perform(post("/api/masini")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Pretul trebuie sa fie strict mai mare decat 0"));
    }

    @Test
    void testCreateMasinaDuplicate() throws Exception {
        MasinaRequest request = new MasinaRequest("Dacia", "Logan", 2020, 10000.0, 100, "Benzina", 90, "a@b.ro");

        when(masinaService.createMasina(any(MasinaRequest.class)))
                .thenThrow(new DuplicateMasinaException("O masina cu aceeasi marca, model si an exista deja"));

        mockMvc.perform(post("/api/masini")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("O masina cu aceeasi marca, model si an exista deja"));
    }
}
