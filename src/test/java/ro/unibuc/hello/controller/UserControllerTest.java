package ro.unibuc.hello.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.hamcrest.CoreMatchers.is;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.dto.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ro.unibuc.hello.controller.ListingControllerTest.asJsonString;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void registerUser() throws Exception{
        User user = new User("Alexandru", "Voiculescu");

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user)))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId", is("621f6cdd32cee55db0e3d88e")));
    }
/*
    @Mock
    UserRepository mockUserRepository;

    @BeforeEach
    void setUp() {

    }

    @Test
    void registerUser() {
        User user = new User(
            "Florian",
            "Marcu"
        );
        String userId = user.userId;
        UserController controller = new UserController();
        controller.registerUser(user);
        when(mockUserRepository.findUserById(userId)).thenReturn(user);
        Assertions.assertEquals(user.getUserId(),  userId);

    }

    @Test
    void getAllUsers() {
        UserController controller = new UserController();
        List<User> users = controller.getAllUsers();
        when(mockUserRepository.findAll()).thenReturn(users);

    }

 */
}