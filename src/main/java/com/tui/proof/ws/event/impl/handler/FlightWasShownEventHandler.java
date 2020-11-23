package com.tui.proof.ws.event.impl.handler;

import com.tui.proof.ws.event.impl.event.FlightWasShownEvent;
import com.tui.proof.ws.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@RequiredArgsConstructor
@PropertySource("classpath:application.yml")
public class FlightWasShownEventHandler implements ApplicationListener<FlightWasShownEvent> {

    @Value("${flight.availability.minutes:15}")
    private Long availabilityTime;
    private final FlightRepository flightRepository;

    @Override
    public void onApplicationEvent(FlightWasShownEvent flightWasShownEvent) {
        try {
            Thread.sleep(1000 * 60 * availabilityTime);
            log.info("flight " + flightWasShownEvent.getFlight() + " is no longer valid");
            flightRepository.deleteFlight(flightWasShownEvent.getFlight());
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }
}
