package com.tui.proof.ws.event.impl.handler;

import com.tui.proof.ws.event.impl.event.DeleteBookingFlightEvent;
import com.tui.proof.ws.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class DeleteBookingFlightEventHandler implements ApplicationListener<DeleteBookingFlightEvent> {

    private final BookingRepository bookingRepository;

    @Override
    public void onApplicationEvent(DeleteBookingFlightEvent event) {
        Optional.ofNullable(bookingRepository.findById(event.getId()))
                .ifPresentOrElse(
                        booking -> booking.getFlights().remove(event.getFlight()),
                        () -> { throw new NoSuchElementException("Booking with id " + event.getId() + " not found"); }
                );
    }
}
