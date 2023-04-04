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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.unibuc.hello.dto.Greeting;
import ro.unibuc.hello.dto.Medicament;
import ro.unibuc.hello.service.HelloWorldService;
import ro.unibuc.hello.service.MedicamentService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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


        // Assert
        Assertions.assertEquals(result.getResponse().getContentAsString(), medicamentTest.toString());
    }

    @Test
    void test_getMedicament() throws Exception {
        // Arrange
        String [] str = new String[]{"aa","bb"};
        String name = "Medicamente";

        Medicament medicamentTest = new Medicament("Nurofen",str);

        //medicamentService.addMedicamente(name,medicamentTest.getName(),medicamentTest.getIngredients());
        when(medicamentService.getMedicament(name,1)).thenReturn(medicamentTest);

        // Act
        MvcResult result = mockMvc.perform(get("/medicament/1")
                        .content(objectMapper.writeValueAsString(medicamentTest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        Assertions.assertEquals(result.getResponse().getContentAsString(), medicamentTest.toString());
    }

    @Test
    void test_addMedicament() throws Exception{
        // Arrange
        String [] str = new String[]{"aa","bb"};
        String name = "Medicamente";

        Medicament medicamentTest = new Medicament("Nurofen",str);


        when(medicamentService.addMedicamente(name,medicamentTest.getName(),medicamentTest.getIngredients())).thenReturn(medicamentTest.toString());

        // Act
        MvcResult result = mockMvc.perform(post("/addmedicament",medicamentTest)
                        .content(objectMapper.writeValueAsString(medicamentTest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

//        MvcResult result = mockMvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"userName\":\"testUserDetails\",\"firstName\":\"xxx\",\"lastName\":\"xxx\",\"password\":\"xxx\"}"))
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(status().isOk())
//                .andReturn();

        // Assert
        Assertions.assertEquals(result.getResponse().getContentAsString(), medicamentTest.toString());
    }

//    @Test
//    void test_editMedicament() throws Exception{
//        // Arrange
//        String [] str = new String[]{"aa","bb"};
//        String name = "Medicamente";
//
//        Medicament medicamentTest = new Medicament("Nurofen",str);
//
//
//        when(medicamentService.editMedicament(name,medicamentTest.getName(),medicamentTest.getIngredients(),medicamentTest.getId())).thenReturn(medicamentTest.toString());
//        // Act
//        MvcResult result = mockMvc.perform(put("/editmedicament",medicamentTest)
//                        .content(objectMapper.writeValueAsString(medicamentTest))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andReturn();
//
////        MvcResult result = mockMvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON)
////                        .content("{\"userName\":\"testUserDetails\",\"firstName\":\"xxx\",\"lastName\":\"xxx\",\"password\":\"xxx\"}"))
////                .andDo(MockMvcResultHandlers.print())
////                .andExpect(status().isOk())
////                .andReturn();
//
//        // Assert
//        Assertions.assertEquals(result.getResponse().getContentAsString(), medicamentTest.toString());
//    }

}
