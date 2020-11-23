package com.tui.proof.ws.service.impl;

import com.tui.proof.ws.dto.Booking;
import com.tui.proof.ws.dto.Flight;
import com.tui.proof.ws.dto.validation.ManualValidationException;
import com.tui.proof.ws.event.EventDispatcher;
import com.tui.proof.ws.event.impl.event.AddBookingFlightEvent;
import com.tui.proof.ws.event.impl.event.DeleteBookingEvent;
import com.tui.proof.ws.event.impl.event.DeleteBookingFlightEvent;
import com.tui.proof.ws.repository.BookingRepository;
import com.tui.proof.ws.service.BookingService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
@Log4j2
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final Validator validator;
    private final EventDispatcher eventDispatcher;

    @Override
    public Booking findById(Long id) {
        return bookingRepository.findById(id);
    }

    @Override
    public Booking add(Booking booking) {
        return bookingRepository.add(booking);
    }

    @Override
    @Validated
    public void confirm(Long id) {
        Optional.ofNullable(bookingRepository.findById(id)).ifPresentOrElse(
                this::confirm,
                () -> {
                    throw new NoSuchElementException("Booking with id " + id + " not found");
                }
        );
    }

    private void confirm(Booking booking) {
        Errors errors = new BeanPropertyBindingResult(booking, booking.getClass().getName());
        validator.validate(booking, errors);

        if (errors.hasErrors()) {
            throw new ManualValidationException(errors);
        }
    }

    @Override
    public void addFlight(Long id, Flight flight) {
        Optional.ofNullable(bookingRepository.findById(id))
                .ifPresentOrElse(
                        booking -> eventDispatcher.dispatch(new AddBookingFlightEvent(id, flight)),
                        () -> { throw new NoSuchElementException("Booking with id " + id + " not found"); }
                );
    }

    @Override
    public void delete(Long id) {
        Optional.ofNullable(bookingRepository.findById(id)).ifPresentOrElse(
                booking -> eventDispatcher.dispatch(new DeleteBookingEvent(id)),
                () -> { throw new NoSuchElementException("Booking with id " + id + " not found"); }
        );
    }

    @Override
    public void deleteFlight(Long id, Flight flight) {
        Optional.ofNullable(bookingRepository.findById(id))
                .ifPresentOrElse(
                        booking -> eventDispatcher.dispatch(new DeleteBookingFlightEvent(id, flight)),
                        () -> { throw new NoSuchElementException("Booking with id " + id + " not found"); }
                );
    }
}
