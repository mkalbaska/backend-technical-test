package com.tui.proof.ws.controller;

import com.tui.proof.ws.dto.Booking;
import com.tui.proof.ws.dto.Flight;
import com.tui.proof.ws.dto.validation.IsAvailableFlight;
import com.tui.proof.ws.event.EventDispatcher;
import com.tui.proof.ws.event.impl.event.AddBookingFlightEvent;
import com.tui.proof.ws.event.impl.event.ConfirmBookingEvent;
import com.tui.proof.ws.event.impl.event.DeleteBookingEvent;
import com.tui.proof.ws.event.impl.event.DeleteBookingFlightEvent;
import com.tui.proof.ws.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
    @Operation(summary = "Create a new booking",
            requestBody =  @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Booking.class))}),
            responses = {
                    @ApiResponse(responseCode = "201", description = "New booking added",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Booking.class))
                            }),
                    @ApiResponse(responseCode = "400", description = "Booking request is invalid",
                            content = @Content)
            },
            security = { @SecurityRequirement(name = "bearer-key") }
    )
    Booking booking(@RequestBody @Valid Booking booking) {
        return bookingService.add(booking);
    }

    @GetMapping("/booking/{id}")
    @Operation(summary = "Query booking details",
            parameters = {
                    @Parameter(name = "id", description = "Booking id")
            },
            responses = {
                    @ApiResponse(responseCode = "200",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Booking.class))
                            })
            },
            security = { @SecurityRequirement(name = "bearer-key") }
    )
    Booking booking(@PathVariable("id") Long id) {
        return bookingService.findById(id);
    }

    @DeleteMapping("/booking/{id}")
    @Operation(summary = "Delete a booking",
            parameters = {
                    @Parameter(name = "id", description = "Booking id")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Booking was deleted")
            },
            security = { @SecurityRequirement(name = "bearer-key") }
    )
    void deleteBooking(@PathVariable("id") Long id) {
        eventDispatcher.dispatch(new DeleteBookingEvent(id));
    }

    @PutMapping("/booking/{id}/flight")
    @Operation(summary = "Add a flight to a booking",
            parameters = {
                    @Parameter(name = "id", description = "Booking id")
            },
            requestBody =  @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Flight.class))}),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Flight was added")
            },
            security = { @SecurityRequirement(name = "bearer-key") }
    )
    void addFlight(@PathVariable("id") Long id, @RequestBody @IsAvailableFlight @Valid Flight flight) {
        eventDispatcher.dispatch(new AddBookingFlightEvent(id, flight));
    }

    @DeleteMapping("/booking/{id}/flight")
    @Operation(summary = "Delete a flight from a booking",
            parameters = {
                    @Parameter(name = "id", description = "Booking id")
            },
            requestBody =  @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Flight.class))}),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Flight was deleted")
            },
            security = { @SecurityRequirement(name = "bearer-key") }
    )
    void deleteFlight(@PathVariable("id") Long id, @RequestBody @IsAvailableFlight @Valid Flight flight) {
        eventDispatcher.dispatch(new DeleteBookingFlightEvent(id, flight));
    }

    @PostMapping("/booking/confirm/{id}")
    @Operation(summary = "Confirm a booking",
            parameters = {
                    @Parameter(name = "id", description = "Booking id")
            },
            requestBody =  @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Flight.class))}),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Booking was confirmed")
            },
            security = { @SecurityRequirement(name = "bearer-key") }
    )
    void confirm(@PathVariable("id") Long id) {
        eventDispatcher.dispatch(new ConfirmBookingEvent(id));
    }

}
