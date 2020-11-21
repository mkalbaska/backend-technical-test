package com.tui.proof.ws.event.impl;

import com.tui.proof.ws.dto.Flight;
import com.tui.proof.ws.event.Event;
import com.tui.proof.ws.event.EventHandler;
import com.tui.proof.ws.event.FlightWasShownEvent;
import com.tui.proof.ws.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Log4j2
@RequiredArgsConstructor
@PropertySource("classpath:application.properties")
public class FlightWasShownEventHandler implements EventHandler {

    @Value("${flight.availability.minutes:15}")
    private Long availabilityTime;
    private final FlightRepository flightRepository;

    @Override
    public void onEvent(Event event) {
        CompletableFuture.runAsync(() -> waitAndDelete(event));
    }

    @Override
    public Class<? extends Event> getEventType() {
        return FlightWasShownEvent.class;
    }


    private void waitAndDelete(Event event) {
        try {
            Thread.sleep(1000 * 60 * availabilityTime);
            Flight flight = ((FlightWasShownEvent)event).getFlight();
            log.info("flight " + flight + " is no longer valid");
            flightRepository.deleteFlight(flight);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }
}
