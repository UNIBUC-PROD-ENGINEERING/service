package ro.unibuc.hello.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.unibuc.hello.data.BookingEntity;
import ro.unibuc.hello.service.BookingService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class BookingControllerTest {

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private BookingController bookingController;

    private MockMvc mockMvc;

    private final String apartmentId1 = "apartmentId1";
    private final String apartmentId2 = "apartmentId2";
    private final String userId1 = "user1";
    private BookingEntity booking1;
    private BookingEntity booking2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookingController).build();

        booking1 = new BookingEntity(
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 1, 5),
                apartmentId1,
                userId1
        );
        booking1.setId("booking1");

        booking2 = new BookingEntity(
                LocalDate.of(2025, 1, 10),
                LocalDate.of(2025, 1, 15),
                apartmentId2,
                userId1
        );
        booking2.setId("booking2");

    }

    @Test
    void getAllBookings() throws Exception {
        List<BookingEntity> bookings = Arrays.asList(booking1, booking2);
        when(bookingService.getAllBookings()).thenReturn(bookings);

        mockMvc.perform(get("/bookings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("booking1"))
                .andExpect(jsonPath("$[0].apartmentId").value(apartmentId1))
                .andExpect(jsonPath("$[0].userId").value(userId1))
                .andExpect(jsonPath("$[1].id").value("booking2"))
                .andExpect(jsonPath("$[1].apartmentId").value(apartmentId2));
    }

    @Test
    void getBookingById() throws Exception {
        when(bookingService.getBookingById("booking1")).thenReturn(Optional.of(booking1));

        mockMvc.perform(get("/bookings/booking1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("booking1"))
                .andExpect(jsonPath("$.apartmentId").value(apartmentId1))
                .andExpect(jsonPath("$.userId").value(userId1));
    }

    @Test
    void createBooking_Success() throws Exception {
        BookingEntity newBooking = new BookingEntity(
                LocalDate.of(2025, 2, 1),
                LocalDate.of(2025, 2, 5),
                apartmentId1,
                userId1
        );
        newBooking.setId("newBooking");

        when(bookingService.createBooking(any(BookingEntity.class))).thenReturn(newBooking);

        mockMvc.perform(post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"startDate\":\"2025-02-01\",\"endDate\":\"2025-02-05\",\"apartmentId\":\"apartment1\",\"userId\":\"user1\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("newBooking"))
                .andExpect(jsonPath("$.apartmentId").value(apartmentId1))
                .andExpect(jsonPath("$.userId").value(userId1));
    }

    @Test
    void createBooking_ApartmentNotFound() throws Exception {
        when(bookingService.createBooking(any(BookingEntity.class)))
                .thenThrow(new IllegalArgumentException("Apartment is not available for the selected dates."));

        mockMvc.perform(post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"startDate\":\"2025-01-01\",\"endDate\":\"2025-01-05\",\"apartmentId\":\"apartment1\",\"userId\":\"user1\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Apartment is not available for the selected dates."));
    }


    @Test
    void deleteBooking() throws Exception {
        doNothing().when(bookingService).deleteBooking("booking1");

        mockMvc.perform(delete("/bookings/booking1"))
                .andExpect(status().isOk());

        verify(bookingService, times(1)).deleteBooking("booking1");
    }

    @Test
    void getBookingsForApartment() throws Exception{
        List<BookingEntity> apartmentBookings = Collections.singletonList(booking1);
        when(bookingService.getBookingsForApartment(apartmentId1)).thenReturn(apartmentBookings);

        mockMvc.perform(get("/bookings/by-apartment/" + apartmentId1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("booking1"))
                .andExpect(jsonPath("$[0].apartmentId").value(apartmentId1));
    }

    @Test
    void getBookingsForApartmentAndUser() throws Exception {
        List<BookingEntity> apartmentUserBookings = Collections.singletonList(booking1);
        when(bookingService.getBookingsForApartmentAndUser(apartmentId1, userId1)).thenReturn(apartmentUserBookings);

        mockMvc.perform(get("/bookings/by-apartment-and-user")
                        .param("apartmentId", apartmentId1)
                        .param("userId", userId1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("booking1"))
                .andExpect(jsonPath("$[0].apartmentId").value(apartmentId1))
                .andExpect(jsonPath("$[0].userId").value(userId1));
    }

    @Test
    void checkAvailability_Available() throws Exception {
        when(bookingService.isApartmentAvailable(eq(apartmentId1), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(true);

        mockMvc.perform(get("/bookings/check-availability/" + apartmentId1)
                        .param("startDate", "2025-02-01")
                        .param("endDate", "2025-02-05"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.apartmentId").value(apartmentId1))
                .andExpect(jsonPath("$.startDate[0]").value(2025))
                .andExpect(jsonPath("$.startDate[1]").value(2))
                .andExpect(jsonPath("$.startDate[2]").value(1))
                .andExpect(jsonPath("$.endDate[0]").value(2025))
                .andExpect(jsonPath("$.endDate[1]").value(2))
                .andExpect(jsonPath("$.endDate[2]").value(5))
                .andExpect(jsonPath("$.available").value(true));

    }

    @Test
    void testCheckAvailability_NotAvailable() throws Exception {
        when(bookingService.isApartmentAvailable(eq(apartmentId1), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(false);

        mockMvc.perform(get("/bookings/check-availability/" + apartmentId1)
                        .param("startDate", "2025-01-01")
                        .param("endDate", "2025-01-05"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.apartmentId").value(apartmentId1))
                .andExpect(jsonPath("$.startDate[0]").value(2025))
                .andExpect(jsonPath("$.startDate[1]").value(1))
                .andExpect(jsonPath("$.startDate[2]").value(1))
                .andExpect(jsonPath("$.endDate[0]").value(2025))
                .andExpect(jsonPath("$.endDate[1]").value(1))
                .andExpect(jsonPath("$.endDate[2]").value(5))
                .andExpect(jsonPath("$.available").value(false));
    }

    @Test
    void testCheckAvailability_ApartmentNotFound() throws Exception {
        when(bookingService.isApartmentAvailable(eq("nonexistentApartment"), any(LocalDate.class), any(LocalDate.class)))
                .thenThrow(new IllegalArgumentException("Apartment with ID nonexistentApartment does not exist."));

        mockMvc.perform(get("/bookings/check-availability/nonexistentApartment")
                        .param("startDate", "2025-02-01")
                        .param("endDate", "2025-02-05"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Apartment with ID nonexistentApartment does not exist."));
    }

    @Test
    void getAvailableApartments() throws Exception {
        List<String> availableApartmentIds = Arrays.asList(apartmentId2);
        when(bookingService.findAvailableApartmentIds(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(availableApartmentIds);

        mockMvc.perform(get("/bookings/available-apartments")
                        .param("startDate", "2025-01-01")
                        .param("endDate", "2025-01-05"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.startDate[0]").value(2025))
                .andExpect(jsonPath("$.startDate[1]").value(1))
                .andExpect(jsonPath("$.startDate[2]").value(1))
                .andExpect(jsonPath("$.endDate[0]").value(2025))
                .andExpect(jsonPath("$.endDate[1]").value(1))
                .andExpect(jsonPath("$.endDate[2]").value(5))
                .andExpect(jsonPath("$.availableApartmentIds[0]").value(apartmentId2))
                .andExpect(jsonPath("$.count").value(1));
    }
}