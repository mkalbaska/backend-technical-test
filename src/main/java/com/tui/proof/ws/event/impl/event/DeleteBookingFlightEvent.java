package com.tui.proof.ws.event.impl.event;

import com.tui.proof.ws.dto.Flight;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class DeleteBookingFlightEvent extends ApplicationEvent {

    private final Long id;
    private final Flight flight;

    public DeleteBookingFlightEvent(Long id, Flight flight) {
        super(id);
        this.id = id;
        this.flight = flight;
    }
}
