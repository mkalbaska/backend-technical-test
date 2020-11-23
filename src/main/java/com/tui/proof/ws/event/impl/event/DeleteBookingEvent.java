package com.tui.proof.ws.event.impl.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class DeleteBookingEvent extends ApplicationEvent {

    private final Long bookingId;

    public DeleteBookingEvent(Long bookingId) {
        super(bookingId);
        this.bookingId = bookingId;
    }
}
