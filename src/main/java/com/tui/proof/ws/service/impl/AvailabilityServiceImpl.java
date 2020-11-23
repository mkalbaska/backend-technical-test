package com.tui.proof.ws.service.impl;

import com.tui.proof.ws.dto.Availability;
import com.tui.proof.ws.dto.Flight;
import com.tui.proof.ws.event.EventDispatcher;
import com.tui.proof.ws.event.impl.event.FlightWasShownEvent;
import com.tui.proof.ws.repository.FlightRepository;
import com.tui.proof.ws.service.AvailabilityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AvailabilityServiceImpl implements AvailabilityService {

    private final FlightRepository flightRepository;
    private final EventDispatcher eventDispatcher;

    @Override
    public List<Flight> flight(Availability availabilityRequest) {
        return flightRepository.flight().stream()
                .peek(it -> eventDispatcher.dispatch(new FlightWasShownEvent(it)))
                .collect(Collectors.toList());
    }
}
