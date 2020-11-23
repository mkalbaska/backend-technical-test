package com.tui.proof.ws.event.impl.event;

import com.tui.proof.ws.dto.Flight;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class FlightWasShownEvent extends ApplicationEvent {

    private final Flight flight;

    public FlightWasShownEvent(Flight flight) {
        super(flight);
        this.flight = flight;
    }

}
