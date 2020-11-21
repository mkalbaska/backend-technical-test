package com.tui.proof.ws.event;

import com.tui.proof.ws.dto.Flight;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FlightWasShownEvent implements Event {

    private final Flight flight;

    @Override
    public Class<? extends Event> getType() {
        return FlightWasShownEvent.class;
    }
}
