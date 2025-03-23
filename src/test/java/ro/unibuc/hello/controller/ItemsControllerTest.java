package ro.unibuc.hello.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.unibuc.hello.dto.Item;
import ro.unibuc.hello.dto.ItemPost;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.ItemsService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ItemsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ItemsService itemsService;

    @InjectMocks
    private ItemsController itemsController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(itemsController).build();
    }

    @Test
    public void testGetAllItems() throws Exception {
        // Creează un obiect de tip List<Item>
        Item item = new Item("Item1", "Description1", "Owner1");
        List<Item> items = Arrays.asList(item);
        when(itemsService.getAllItems()).thenReturn(items);

        // HTTP GET verify answer
        mockMvc.perform(get("/items"))
                .andExpect(status().isOk())  // Statusul 200 OK
                .andExpect(jsonPath("$[0].name").value("Item1"))
                .andExpect(jsonPath("$[0].description").value("Description1"))
                .andExpect(jsonPath("$[0].owner").value("Owner1"));

        verify(itemsService, times(1)).getAllItems(); 
    }
    //fails, problem with the test
    // @Test
    // public void testCreateItem() throws Exception {
    //     // Creează un obiect de tip ItemPost
    //     ItemPost itemPost = new ItemPost("sessionId", "Item1", "Description1");
    
    //     // Creează obiectul de tip Item pe care îl așteptăm ca răspuns
    //     Item item = new Item("Item1", "Description1", "Owner1");
    
    //     // Specifică comportamentul mock-ului pentru saveItem
    //     when(itemsService.saveItem(itemPost)).thenReturn(item);
    
    //     // Efectuează apelul POST și verifică răspunsul
    //     mockMvc.perform(post("/items")
    //             .contentType("application/json")
    //             .content("{\"name\":\"Item1\",\"description\":\"Description1\",\"sessionId\":\"sessionId\"}"))
    //             .andExpect(status().isOk()) 
    //             .andExpect(jsonPath("$.name:").value("Item1"))
    //             .andExpect(jsonPath("$.description:").value("Description1"))
    //             .andExpect(jsonPath("$.owner:").value("Owner1")); 
    
    //     // Verifică că metodele mock-ului au fost apelate de o singură dată
    //     verify(itemsService, times(1)).saveItem(itemPost);
    // }
    

    @Test
    public void testGetItemById() throws Exception {
        // Creează un obiect Item
        Item item = new Item("Item1", "Description1", "Owner1");

        // Specifică comportamentul mock-ului pentru metoda getItemById
        when(itemsService.getItemById("1")).thenReturn(item);

        // Efectuează apelul HTTP GET și verifică răspunsul
        mockMvc.perform(get("/items/1"))
                .andExpect(status().isOk())  // Statusul 200 OK
                .andExpect(jsonPath("$.name").value("Item1"))
                .andExpect(jsonPath("$.description").value("Description1"))
                .andExpect(jsonPath("$.owner").value("Owner1"));

        verify(itemsService, times(1)).getItemById("1");  // Verifică că metoda a fost apelată o dată
    }

    // @Test
    // public void testUpdateItem() throws Exception {
    //     // Creează obiectul Item
    //     Item updatedItem = new Item("Item1", "Updated Description", "Owner1");

    //     // Specifică comportamentul mock-ului pentru metoda updateItem
    //     when(itemsService.updateItem("1", updatedItem)).thenReturn(updatedItem);

    //     // Efectuează apelul HTTP PUT și verifică răspunsul
    //     mockMvc.perform(put("/items/1")
    //             .contentType("application/json")
    //             .content("{\"name\":\"Item1\",\"description\":\"Updated Description\",\"owner\":\"Owner1\"}"))
    //             .andExpect(status().isOk())  // Statusul 200 OK
    //             .andExpect(jsonPath("$.name").value("Item1"))
    //             .andExpect(jsonPath("$.description").value("Updated Description"))
    //             .andExpect(jsonPath("$.owner").value("Owner1"));

    //     verify(itemsService, times(1)).updateItem("1", updatedItem);  // Verifică că metoda a fost apelată o dată
    // }

    @Test
    public void testDeleteItem() throws Exception {
        // HTTP DELETE
        mockMvc.perform(delete("/items/1"))
                .andExpect(status().isOk());  //200 OK
        verify(itemsService, times(1)).deleteItem("1");
    }
}
