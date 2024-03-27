package ro.unibuc.hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.dto.UserDto;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetUser() throws Exception {
        // Arrange
        UserEntity userEntity = new UserEntity("John", "Doe", 30, "johndoe");
        when(userService.getUser("1")).thenReturn(userEntity);

        // Act
        MvcResult result = mockMvc.perform(get("/getUser/1"))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        assertEquals(objectMapper.writeValueAsString(userEntity), result.getResponse().getContentAsString());
    }

    // @Test
    // void testAddUser() throws Exception {
    //     // Arrange
    //     UserDto userDto = new UserDto("John", "Doe", 30, "johndoe");

    //     // Act
    //     MvcResult result = mockMvc.perform(post("/addUser")
    //             .content(objectMapper.writeValueAsString(userDto))
    //             .contentType(MediaType.APPLICATION_JSON))
    //             .andExpect(status().isOk())
    //             .andReturn();

    //     String response = result.getResponse().getContentAsString();
    //     System.out.println("testAddUser" + response);
    //     // Assert
    //     assertEquals("User added", response);
    // }

    // @Test
    // void testDeleteUserById() throws Exception {
    //     // Arrange
    //     UserEntity userEntity = new UserEntity("John", "Doe", 30, "johndoe");

    //     // Act
    //     MvcResult result = mockMvc.perform(delete("/deleteUser/1"))
    //             .andExpect(status().isOk())
    //             .andReturn();


    //     String response = result.getResponse().getContentAsString();
    //     System.out.println("testDeleteUserById" + response);
    //     // Assert
    //     assertEquals("User deleted", response);
    // }

    // @Test
    // void testGetUserNotFound() throws Exception {
    //     EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> mockMvc.perform(get("/getUser/1234567890")));
    //     assertNotNull(exception, "Exception");
    // }
    

    // @Test
    // void testDeleteUserByIdNotFound() throws Exception {
    //     // Arrange
    //     when(userService.deleteUserById("1")).thenThrow(new EntityNotFoundException("User not found with id: 1"));

    //     // Act & Assert
    //     assertThrows(EntityNotFoundException.class, () -> mockMvc.perform(delete("/deleteUser/1")));
    // }
}
