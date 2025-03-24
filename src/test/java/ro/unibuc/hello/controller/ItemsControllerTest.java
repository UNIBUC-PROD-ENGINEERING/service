package ro.unibuc.hello.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ro.unibuc.hello.auth.AuthInterceptor;
import ro.unibuc.hello.data.SessionEntity;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.dto.ItemPostRequest;
import ro.unibuc.hello.dto.ItemWithOwner;
import ro.unibuc.hello.dto.User;
import ro.unibuc.hello.permissions.ItemPermissionChecker;
import ro.unibuc.hello.service.ItemsService;
import ro.unibuc.hello.service.SessionsService;

public class ItemsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ItemPermissionChecker permissionChecker;

    @Mock
    private ItemsService itemsService;

    @Mock
    private SessionsService sessionsService;

    @InjectMocks
    private ItemsController itemsController;

    @InjectMocks
    private AuthInterceptor authInterceptor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(itemsController)
            .addInterceptors(authInterceptor)
            .build();

        when(sessionsService.getValidSession(anyString())).thenReturn(new SessionEntity("1", new UserEntity("11", "user 1", "username1", "password1"), null));
    }

    @Test
    public void testGetAllItems() throws Exception {
        // Creează un obiect de tip List<Item>
        ItemWithOwner item = new ItemWithOwner("21", "Item1", "Description1", new User("11", "user 1"));
        List<ItemWithOwner> items = Arrays.asList(item);
        when(itemsService.getAllItems()).thenReturn(items);

        // HTTP GET verify answer
        mockMvc.perform(get("/items"))
                .andExpect(status().isOk())  // Statusul 200 OK
                .andExpect(jsonPath("$[0].name").value("Item1"))
                .andExpect(jsonPath("$[0].description").value("Description1"))
                .andExpect(jsonPath("$[0].owner.name").value("user 1"));

        verify(itemsService, times(1)).getAllItems(); 
    }

    @Test
    public void testCreateItem() throws Exception {
        // Creează obiectul de tip Item pe care îl așteptăm ca răspuns
        ItemWithOwner item = new ItemWithOwner("21", "Item1", "Description1", new User("11", "user 1"));

        // Specifică comportamentul mock-ului pentru saveItem
        when(itemsService.saveItem(anyString(), any(ItemPostRequest.class))).thenReturn(item);
    
        // Efectuează apelul POST și verifică răspunsul
        mockMvc.perform(post("/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Item1\",\"description\":\"Description1\"}")
                .header("X-Session-Id", "1"))
                .andExpect(status().isOk()) 
                .andExpect(jsonPath("$.name").value("Item1"))
                .andExpect(jsonPath("$.description").value("Description1"))
                .andExpect(jsonPath("$.owner.name").value("user 1")); 
    
        // Verifică că metodele mock-ului au fost apelate de o singură dată
        verify(itemsService, times(1)).saveItem(anyString(), any(ItemPostRequest.class));
    }

    @Test
    public void testGetItemById() throws Exception {
        // Creează un obiect Item
        ItemWithOwner item = new ItemWithOwner("21", "Item1", "Description1", new User("11", "user 1"));

        // Specifică comportamentul mock-ului pentru metoda getItemById
        when(itemsService.getItemById("1")).thenReturn(item);

        // Efectuează apelul HTTP GET și verifică răspunsul
        mockMvc.perform(get("/items/1"))
                .andExpect(status().isOk())  // Statusul 200 OK
                .andExpect(jsonPath("$.name").value("Item1"))
                .andExpect(jsonPath("$.description").value("Description1"))
                .andExpect(jsonPath("$.owner.name").value("user 1"));

        verify(itemsService, times(1)).getItemById("1");  // Verifică că metoda a fost apelată o dată
    }

    @Test
    public void testUpdateItem() throws Exception {
        // Creează obiectul de tip Item pe care îl așteptăm ca răspuns
        ItemWithOwner updatedItem = new ItemWithOwner("1", "Updated item", "Updated Description", new User("11", "user 1"));

        // Specifică comportamentul mock-ului pentru metoda updateItem
        when(itemsService.updateItem(anyString(), any(ItemPostRequest.class))).thenReturn(updatedItem);

        // Efectuează apelul HTTP PUT și verifică răspunsul
        mockMvc.perform(put("/items/1")
                .contentType("application/json")
                .content("{\"name\":\"Updated item\",\"description\":\"Updated Description\"}")
                .header("X-Session-Id", "1"))
                .andExpect(status().isOk())  // Statusul 200 OK
                .andExpect(jsonPath("$.name").value("Updated item"))
                .andExpect(jsonPath("$.description").value("Updated Description"))
                .andExpect(jsonPath("$.owner.name").value("user 1"));

        verify(itemsService, times(1)).updateItem(anyString(), any(ItemPostRequest.class));  // Verifică că metoda a fost apelată o dată
    }

    @Test
    public void testDeleteItem() throws Exception {
        // HTTP DELETE
        mockMvc.perform(delete("/items/1")
                .header("X-Session-Id", "1"))
                .andExpect(status().isOk());  //200 OK
        verify(itemsService, times(1)).deleteItem("1");
    }
}
