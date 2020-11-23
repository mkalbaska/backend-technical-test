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
    void addFlight() {
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
        assertEquals(2, bookingRepository.findById(1L).getFlights().size());
    }

    @Test
    void deleteNotExistingFlight() {
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
        assertEquals(1, bookingRepository.findById(1L).getFlights().size());
    }

    @Test
    void deleteFlight() {
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
}