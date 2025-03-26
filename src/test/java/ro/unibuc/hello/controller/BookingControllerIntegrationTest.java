package ro.unibuc.hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import ro.unibuc.hello.data.ApartmentEntity;
import ro.unibuc.hello.data.BookingEntity;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.repository.ApartmentRepository;
import ro.unibuc.hello.repository.BookingRepository;
import ro.unibuc.hello.repository.UserRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@Tag("IntegrationTest")
public class BookingControllerIntegrationTest {

    @Container
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0.20")
            .withExposedPorts(27017)
            .withSharding();

    @BeforeAll
    public static void setUp() {
        mongoDBContainer.start();
    }

    @AfterAll
    public static void tearDown() {
        mongoDBContainer.stop();
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        final String MONGO_URL = "mongodb://localhost:";
        final String PORT = String.valueOf(mongoDBContainer.getMappedPort(27017));

        registry.add("mongodb.connection.url", () -> MONGO_URL + PORT);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Autowired
    private UserRepository userRepository;

    private ApartmentEntity apartment1;
    private ApartmentEntity apartment2;
    private UserEntity user1;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void cleanUpAndAddTestData() {
        bookingRepository.deleteAll();
        apartmentRepository.deleteAll();
        userRepository.deleteAll();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        user1 = new UserEntity();
        user1.setId("user1");
        user1.setName("Test User");
        user1.setEmail("test@example.com");
        userRepository.save(user1);

        apartment1 = new ApartmentEntity();
        apartment1.setId("apartment1");
        apartment1.setTitle("Luxury Apartment");
        apartment1.setLocation("City Center");
        apartment1.setPricePerNight(150.0);
        apartment1.setUserId("user1");
        apartmentRepository.save(apartment1);

        apartment2 = new ApartmentEntity();
        apartment2.setId("apartment2");
        apartment2.setTitle("Beach House");
        apartment2.setLocation("Beachfront");
        apartment2.setPricePerNight(200.0);
        apartment2.setUserId("user1");
        apartmentRepository.save(apartment2);

        BookingEntity booking1 = new BookingEntity();
        booking1.setId("booking1");
        booking1.setApartmentId("apartment1");
        booking1.setUserId("user1");
        booking1.setStartDate(LocalDate.of(2025, 1, 1));
        booking1.setEndDate(LocalDate.of(2025, 1, 5));
        bookingRepository.save(booking1);
    }

    @Test
    public void testGetAllBookings() throws Exception {
        mockMvc.perform(get("/bookings"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value("booking1"))
                .andExpect(jsonPath("$[0].apartmentId").value("apartment1"))
                .andExpect(jsonPath("$[0].userId").value("user1"));
    }

    @Test
    public void testGetBookingById() throws Exception {
        mockMvc.perform(get("/bookings/booking1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("booking1"))
                .andExpect(jsonPath("$.apartmentId").value("apartment1"))
                .andExpect(jsonPath("$.userId").value("user1"));
    }

    @Test
    public void testCreateBooking_Success() throws Exception {
        BookingEntity newBooking = new BookingEntity();
        newBooking.setApartmentId("apartment2");
        newBooking.setUserId("user1");
        newBooking.setStartDate(LocalDate.of(2025, 2, 1));
        newBooking.setEndDate(LocalDate.of(2025, 2, 5));

        mockMvc.perform(post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newBooking)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.apartmentId").value("apartment2"))
                .andExpect(jsonPath("$.userId").value("user1"));

        mockMvc.perform(get("/bookings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void testCreateBooking_ApartmentNotAvailable() throws Exception {
        BookingEntity conflictingBooking = new BookingEntity();
        conflictingBooking.setApartmentId("apartment1");
        conflictingBooking.setUserId("user1");
        conflictingBooking.setStartDate(LocalDate.of(2025, 1, 3));
        conflictingBooking.setEndDate(LocalDate.of(2025, 1, 7));

        mockMvc.perform(post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(conflictingBooking)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists());

        mockMvc.perform(get("/bookings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void testCheckAvailability_Available() throws Exception {
        mockMvc.perform(get("/bookings/check-availability/apartment2")
                        .param("startDate", "2025-01-01")
                        .param("endDate", "2025-01-05"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.apartmentId").value("apartment2"))
                .andExpect(jsonPath("$.available").value(true));
    }

    @Test
    public void testCheckAvailability_NotAvailable() throws Exception {
        mockMvc.perform(get("/bookings/check-availability/apartment1")
                        .param("startDate", "2025-01-01")
                        .param("endDate", "2025-01-05"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.apartmentId").value("apartment1"))
                .andExpect(jsonPath("$.available").value(false));
    }

    @Test
    public void testGetAvailableApartments() throws Exception {
        mockMvc.perform(get("/bookings/available-apartments")
                        .param("startDate", "2025-01-01")
                        .param("endDate", "2025-01-05"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.availableApartmentIds", hasSize(1)))
                .andExpect(jsonPath("$.availableApartmentIds[0]").value("apartment2"))
                .andExpect(jsonPath("$.count").value(1));
    }

    @Test
    public void testDeleteBooking() throws Exception {
        mockMvc.perform(delete("/bookings/booking1"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/bookings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        mockMvc.perform(get("/bookings/check-availability/apartment1")
                        .param("startDate", "2025-01-01")
                        .param("endDate", "2025-01-05"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.available").value(true));
    }

    @Test
    public void testGetBookingsForApartment() throws Exception {
        mockMvc.perform(get("/bookings/by-apartment/apartment1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value("booking1"));

        mockMvc.perform(get("/bookings/by-apartment/apartment2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void testGetBookingsForApartmentAndUser() throws Exception {
        mockMvc.perform(get("/bookings/by-apartment-and-user")
                        .param("apartmentId", "apartment1")
                        .param("userId", "user1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value("booking1"));

        mockMvc.perform(get("/bookings/by-apartment-and-user")
                        .param("apartmentId", "apartment1")
                        .param("userId", "nonexistentUser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}