package com.tui.proof.ws.service.impl;

import com.tui.proof.MainApplication;
import com.tui.proof.ws.dto.Booking;
import com.tui.proof.ws.dto.Flight;
import com.tui.proof.ws.dto.Holder;
import com.tui.proof.ws.dto.Monetary;
import com.tui.proof.ws.dto.validation.ManualValidationException;
import com.tui.proof.ws.repository.FlightRepository;
import com.tui.proof.ws.repository.impl.MockBookingRepository;
import com.tui.proof.ws.service.BookingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MainApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ComponentScan("com.tui.proof.ws")
class BookingServiceImplTest {

    @Autowired
    private BookingService testSubject;

    @MockBean
    private MockBookingRepository bookingRepository;
    @MockBean
    private FlightRepository flightRepository;

    @Test
    void confirmBookingIsNotValid() {
        Mockito.when(flightRepository.isValid(any(Flight.class))).thenReturn(false);

        Mockito.when(bookingRepository.findById(1L)).thenReturn(
                new Booking(
                        new Holder(
                                "", "", "",
                                "", "", "", Collections.singletonList("")
                        ),
                        Collections.emptyList())
        );

        assertThrows(ManualValidationException.class, () -> testSubject.confirm(1L));
    }

    @Test
    void confirmBookingIsValid() {
        Mockito.when(flightRepository.isValid(any(Flight.class))).thenReturn(true);

        Mockito.when(bookingRepository.findById(1L)).thenReturn(
                new Booking(
                        new Holder(
                                "test", "test", "test",
                                "test", "test", "test@test.com", Collections.singletonList("1235468798")
                        ),
                        Collections.singletonList(
                                new Flight("test", "test", LocalDate.now(), LocalTime.now(), new Monetary(BigDecimal.ONE, ""))
                        ))
        );

        assertAll( () -> testSubject.confirm(1L));

    }

    @Test
    void addFlight() throws Exception {
        Mockito.when(bookingRepository.findById(1L)).thenReturn(
                new Booking(
                        new Holder(
                                "test", "test", "test",
                                "test", "test", "test@test.com",
                                Collections.singletonList("1235468798")
                        ),
                        new ArrayList<>(
                                Collections.singletonList(
                                        new Flight("test", "test", LocalDate.now(), LocalTime.now(),
                                                new Monetary(BigDecimal.ONE, ""))
                                )
                        ))
        );
        testSubject.addFlight(1L, new Flight("new", "new", LocalDate.now(), LocalTime.now(),
                new Monetary(BigDecimal.ONE, "")));
        Thread.sleep(2000); //wait for event handler
        assertEquals(2, bookingRepository.findById(1L).getFlights().size());
    }

    @Test
    void deleteNotExistingFlight() throws Exception {
        Flight flight = new Flight("test", "test", LocalDate.now(), LocalTime.now(), new Monetary(BigDecimal.ONE, ""));
        Mockito.when(bookingRepository.findById(1L)).thenReturn(
                new Booking(
                        new Holder(
                                "test", "test", "test",
                                "test", "test", "test@test.com", Collections.singletonList("1235468798")
                        ),
                        new ArrayList<>(Collections.singletonList(flight)))
        );
        testSubject.deleteFlight(1L, new Flight("new", "new", LocalDate.now(), LocalTime.now(),
                new Monetary(BigDecimal.ONE, "")));
        Thread.sleep(2000); //wait for event handler
        assertEquals(1, bookingRepository.findById(1L).getFlights().size());
    }

    @Test
    void deleteFlight() throws Exception {
        Flight flight = new Flight("test", "test", LocalDate.now(), LocalTime.now(),
                new Monetary(BigDecimal.ONE, ""));
        Mockito.when(bookingRepository.findById(1L)).thenReturn(
                new Booking(
                        new Holder(
                                "test", "test", "test",
                                "test", "test", "test@test.com", Collections.singletonList("1235468798")
                        ),
                        new ArrayList<>(Collections.singletonList(flight)))
        );
        testSubject.deleteFlight(1L, flight);
        Thread.sleep(2000); //wait for event handler
        assertEquals(0, bookingRepository.findById(1L).getFlights().size());
    }

    @Test
    void add() {
        Mockito.when(bookingRepository.add(any(Booking.class))).thenCallRealMethod();
        Booking result = testSubject.add(new Booking(
                new Holder(
                        "", "", "",
                        "", "", "", Collections.singletonList("")
                ),
                Collections.emptyList()));

        assertNotNull(result.getId());
    }

    @Test
    void deleteExistingBooking() throws Exception {
        Mockito.when(bookingRepository.findById(1L)).thenReturn(
                new Booking(
                        new Holder(
                                "test", "test", "test",
                                "test", "test", "test@test.com", Collections.singletonList("1235468798")
                        ),
                        new ArrayList<>(Collections.emptyList()))
        );
        Thread.sleep(2000); //wait for event handler

        assertAll(() -> testSubject.delete(1L));
    }

    @Test
    void deleteNonExistingBooking() throws Exception {
        Mockito.when(bookingRepository.findById(2L)).thenReturn(null);
        Thread.sleep(2000); //wait for event handler
        assertThrows(NoSuchElementException.class, () -> testSubject.delete(2L));
    }
}