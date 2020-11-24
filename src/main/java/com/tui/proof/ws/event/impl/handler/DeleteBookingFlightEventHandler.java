package com.tui.proof.ws.event.impl.handler;

import com.tui.proof.ws.event.impl.event.DeleteBookingFlightEvent;
import com.tui.proof.ws.service.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class DeleteBookingFlightEventHandler implements ApplicationListener<DeleteBookingFlightEvent> {

    private final BookingService bookingService;

    @Override
    public void onApplicationEvent(DeleteBookingFlightEvent event) {
        bookingService.deleteFlight(event.getId(), event.getFlight());
    }
}
