package ro.unibuc.hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.unibuc.hello.dto.Greeting;
import ro.unibuc.hello.dto.Medicament;
import ro.unibuc.hello.service.HelloWorldService;
import ro.unibuc.hello.service.MedicamentService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)

public class MedicamentControllerTest {

    @Mock
    private MedicamentService medicamentService;

    @InjectMocks
    private MedicamentController medicamentController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(medicamentController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void test_getMedicamente() throws Exception {

        // Arrange
        String [] str = new String[]{"aa","bb"};

        Medicament medicamentTest = new Medicament("Nurofen",str);

        when(medicamentService.getMedicamente(any())).thenReturn(medicamentTest.toString());

        // Act
        MvcResult result = mockMvc.perform(get("/medicamente")
                        .content(objectMapper.writeValueAsString(medicamentTest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println("AAA");
        System.out.println(result.getResponse().getContentAsString());
        System.out.println(medicamentTest.toString());
        // Assert
        Assertions.assertEquals(result.getResponse().getContentAsString(), medicamentTest.toString());
    }

    @Test
    void test_getMedicament() throws Exception {
        // Arrange
        String [] str = new String[]{"aa","bb"};

        Medicament medicamentTest = new Medicament("Nurofen",str);

        when(medicamentService.getMedicament(any(),any())).thenReturn(medicamentTest);

        // Act
        MvcResult result = mockMvc.perform(get("/medicament/1")
                        .content(objectMapper.writeValueAsString(medicamentTest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        Assertions.assertEquals(result.getResponse().getContentAsString(), medicamentTest.toString());
    }

}
