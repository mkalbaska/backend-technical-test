package com.tui.proof.ws.event.impl.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ConfirmBookingEvent extends ApplicationEvent {

    private final Long id;

    public ConfirmBookingEvent(Long id) {
        super(id);
        this.id = id;
    }
}
