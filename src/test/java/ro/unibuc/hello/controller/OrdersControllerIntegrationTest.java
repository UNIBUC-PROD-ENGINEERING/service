// package ro.unibuc.hello.controller;

// import com.fasterxml.jackson.databind.ObjectMapper;

// import ro.unibuc.hello.dto.Order;

// import org.junit.jupiter.api.*;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.http.MediaType;
// import org.springframework.test.context.DynamicPropertyRegistry;
// import org.springframework.test.context.DynamicPropertySource;
// import org.springframework.test.web.servlet.MockMvc;
// import org.testcontainers.containers.MongoDBContainer;
// import org.testcontainers.junit.jupiter.Container;
// import org.testcontainers.junit.jupiter.Testcontainers;

// import ro.unibuc.hello.service.OrderService;

// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

// @Disabled
// @SpringBootTest
// @AutoConfigureMockMvc
// @Testcontainers
// @Tag("IntegrationTest")
// public class OrdersControllerIntegrationTest {

//     @Container
//     public static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0.20")
//             .withExposedPorts(27017)
//             .withEnv("MONGO_INITDB_ROOT_USERNAME","root") // user
//             .withEnv("MONGO_INITDB_ROOT_PASSWORD", "example") // password
//             .withEnv("MONGO_INITDB_DATABASE", "testdb") // dbname
//             .withCommand("--auth");

//     @BeforeAll
//     public static void setUp() {
//         mongoDBContainer.start();
//     }

//     @AfterAll
//     public static void tearDown() {
//         mongoDBContainer.stop();
//     }

//     @DynamicPropertySource
//     static void setProperties(DynamicPropertyRegistry registry) {
//         final String MONGO_URL = "mongodb://root:example@localhost:";
//         final String PORT = String.valueOf(mongoDBContainer.getMappedPort(27017));

//         registry.add("mongodb.connection.url", () -> MONGO_URL + PORT);
//     }

//     @Autowired
//     private MockMvc mockMvc;

//     @Autowired
//     private OrderService orderService;

//     @BeforeEach
//     public void cleanUpAndAddTestData() {
//         orderService.deleteAllOrders();
        
//         Order greeting1 = new Order("1", "Hello 1");
//         Order greeting2 = new Order("2", "Hello 2");

//         orderService.saveOrder(greeting1);
//         orderService.saveOrder(greeting2);
//     }

//     @Test
//     public void testGetAllOrders() throws Exception {
//         mockMvc.perform(get("/Orders"))
//             .andExpect(status().isOk())
//             .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//             .andExpect(jsonPath("$.length()").value(2))
//             .andExpect(jsonPath("$[0].content").value("Hello 1"))
//             .andExpect(jsonPath("$[1].content").value("Hello 2"));
//     }

//     @Test
//     public void testCreateOrder() throws Exception {
//         Order greeting = new Order("3", "Hello New");

//         mockMvc.perform(post("/Orders")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(new ObjectMapper().writeValueAsString(greeting)))
//                 .andExpect(status().isOk())
//                 .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                 .andExpect(jsonPath("$.id").value("3"))
//                 .andExpect(jsonPath("$.content").value("Hello New"));

//         mockMvc.perform(get("/Orders"))
//                 .andExpect(status().isOk())
//                 .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                 .andExpect(jsonPath("$.length()").value(3));
//     }

//     @Test
//     public void testUpdateOrder() throws Exception {
//         Order greeting = new Order("1", "Hello Updated");

//         mockMvc.perform(put("/Orders/1")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(new ObjectMapper().writeValueAsString(greeting)))
//                 .andExpect(status().isOk())
//                 .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                 .andExpect(jsonPath("$.id").value("1"))
//                 .andExpect(jsonPath("$.content").value("Hello Updated"));

//         mockMvc.perform(get("/Orders"))
//                 .andExpect(status().isOk())
//                 .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                 .andExpect(jsonPath("$.length()").value(2))
//                 .andExpect(jsonPath("$[0].content").value("Hello Updated"))
//                 .andExpect(jsonPath("$[1].content").value("Hello 2"));
//     }

//     @Test
//     public void testDeleteOrder() throws Exception {

//         mockMvc.perform(delete("/Orders/1"))
//             .andExpect(status().isOk());

//         mockMvc.perform(get("/Orders"))
//                 .andExpect(status().isOk())
//                 .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                 .andExpect(jsonPath("$.length()").value(1))
//                 .andExpect(jsonPath("$[0].content").value("Hello 2"));
//     }
// }
