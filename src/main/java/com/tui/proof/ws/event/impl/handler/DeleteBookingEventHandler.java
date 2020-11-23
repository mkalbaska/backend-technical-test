package com.tui.proof.ws.event.impl.handler;

import com.tui.proof.ws.event.impl.event.DeleteBookingEvent;
import com.tui.proof.ws.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class DeleteBookingEventHandler implements ApplicationListener<DeleteBookingEvent> {

    private final BookingRepository bookingRepository;

    @Override
    public void onApplicationEvent(DeleteBookingEvent deleteBookingEvent) {
        bookingRepository.delete(deleteBookingEvent.getBookingId());
    }
}
