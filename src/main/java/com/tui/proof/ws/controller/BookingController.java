package com.tui.proof.ws.controller;

import com.tui.proof.ws.dto.Booking;
import com.tui.proof.ws.dto.Flight;
import com.tui.proof.ws.dto.validation.IsAvailableFlight;
import com.tui.proof.ws.event.EventDispatcher;
import com.tui.proof.ws.event.impl.event.AddBookingFlightEvent;
import com.tui.proof.ws.event.impl.event.DeleteBookingEvent;
import com.tui.proof.ws.event.impl.event.DeleteBookingFlightEvent;
import com.tui.proof.ws.service.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController()
@AllArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final EventDispatcher eventDispatcher;

    @PostMapping("/booking")
    @ResponseStatus(HttpStatus.CREATED)
    Booking booking(@RequestBody @Valid Booking booking) {
        return bookingService.add(booking);
    }

    @GetMapping("/booking/{id}")
    Booking booking(@PathVariable("id") Long id) {
        return bookingService.findById(id);
    }

    @DeleteMapping("/booking/{id}")
    void deleteBooking(@PathVariable("id") Long id) {
        eventDispatcher.dispatch(new DeleteBookingEvent(id));
    }

    @PutMapping("/booking/{id}/flight")
    void addFlight(@PathVariable("id") Long id, @RequestBody @IsAvailableFlight @Valid Flight flight) {
        eventDispatcher.dispatch(new AddBookingFlightEvent(id, flight));
    }

    @DeleteMapping("/booking/{id}/flight")
    void deleteFlight(@PathVariable("id") Long id, @RequestBody @IsAvailableFlight @Valid Flight flight) {
        eventDispatcher.dispatch(new DeleteBookingFlightEvent(id, flight));
    }

    @PostMapping("/booking/confirm/{id}")
    void confirm(@PathVariable("id") Long id) {
        bookingService.confirm(id);
    }

}
