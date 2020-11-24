package com.tui.proof.ws.controller;

import com.tui.proof.ws.dto.Booking;
import com.tui.proof.ws.dto.Flight;
import com.tui.proof.ws.dto.Holder;
import com.tui.proof.ws.dto.Monetary;
import com.tui.proof.ws.repository.BookingRepository;
import com.tui.proof.ws.repository.FlightRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = BookingController.class)
class BookingControllerTest extends AbstractControllerTest {

    @MockBean
    private BookingRepository bookingRepository;
    @MockBean
    private FlightRepository flightRepository;


    @Test
    void createBooking() throws Exception {

        Flight flight = new Flight(
                "company ",
                UUID.randomUUID().toString(),
                LocalDate.now(),
                LocalTime.now().truncatedTo(ChronoUnit.MINUTES),
                new Monetary(
                        new BigDecimal(100),
                        "USD"
                )
        );
        Booking booking = new Booking(
                new Holder(
                        "name", "lastname", "address",
                        "code", "country", "mail@mail.com", Collections.singletonList("1234567890")
                ),
                Collections.singletonList(flight));

        when(flightRepository.isValid(flight)).thenReturn(true);

        String request = objectMapper.writeValueAsString(booking);
        mockMvc.perform(MockMvcRequestBuilders.post("/booking")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + login())
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
        ;
    }

    @Test
    void getBooking() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/booking/1")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + login())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
        ;
    }

    @Test
    void deleteBooking() throws Exception {
        when(bookingRepository.findById(1L)).thenReturn(new Booking(null, Collections.emptyList()));
        mockMvc.perform(MockMvcRequestBuilders.delete("/booking/1")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + login())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void addFlight() throws Exception {
        Flight flight = new Flight(
                "company ",
                UUID.randomUUID().toString(),
                LocalDate.now(),
                LocalTime.now().truncatedTo(ChronoUnit.MINUTES),
                new Monetary(
                        new BigDecimal(100),
                        "USD"
                )
        );
        when(flightRepository.isValid(flight)).thenReturn(true);
        when(bookingRepository.findById(1L)).thenReturn(new Booking(null, Collections.emptyList()));
        mockMvc.perform(MockMvcRequestBuilders.put("/booking/1/flight")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + login())
                .content(objectMapper.writeValueAsString(flight))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    void deleteFlight() throws Exception {
        when(bookingRepository.findById(1L)).thenReturn(new Booking(null, Collections.emptyList()));
        Flight flight = new Flight(
                "company ",
                UUID.randomUUID().toString(),
                LocalDate.now(),
                LocalTime.now().truncatedTo(ChronoUnit.MINUTES),
                new Monetary(
                        new BigDecimal(100),
                        "USD"
                )
        );
        when(flightRepository.isValid(flight)).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.delete("/booking/1/flight")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + login())
                .content(objectMapper.writeValueAsString(flight))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    void confirm() throws Exception {
        when(flightRepository.isValid(any(Flight.class))).thenReturn(true);

        when(bookingRepository.findById(1L)).thenReturn(new Booking(
                new Holder("name", "last", "addr", "postal",
                        "BY", "test@test.com", Collections.singletonList("1234567890")),
                Collections.singletonList(
                        new Flight(
                                "",
                                "",
                                LocalDate.now(),
                                LocalTime.now(),
                                new Monetary(BigDecimal.ONE, "")
                        )
                )));
        mockMvc.perform(MockMvcRequestBuilders.post("/booking/confirm/1")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + login())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}