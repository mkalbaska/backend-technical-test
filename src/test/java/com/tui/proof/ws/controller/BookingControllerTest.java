package com.tui.proof.ws.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tui.proof.ws.dto.Booking;
import com.tui.proof.ws.dto.Flight;
import com.tui.proof.ws.dto.Holder;
import com.tui.proof.ws.dto.Monetary;
import com.tui.proof.ws.repository.FlightRepository;
import com.tui.proof.ws.service.BookingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.Base64Utils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = BookingController.class)
@ComponentScan("com.tui.proof.ws")
class BookingControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BookingService bookingService;
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
                .header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.encodeToString("root:root".getBytes()))
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
        ;
    }

    @Test
    void getBooking() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/booking/1")
                .header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.encodeToString("root:root".getBytes()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
        ;
    }

    @Test
    void deleteBooking() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/booking/1")
                .header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.encodeToString("root:root".getBytes()))
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
        mockMvc.perform(MockMvcRequestBuilders.put("/booking/1/flight")
                .header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.encodeToString("root:root".getBytes()))
                .content(objectMapper.writeValueAsString(flight))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    void deleteFlight() throws Exception {
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
                .header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.encodeToString("root:root".getBytes()))
                .content(objectMapper.writeValueAsString(flight))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    void confirm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/booking/confirm/1")
                .header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.encodeToString("root:root".getBytes()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}