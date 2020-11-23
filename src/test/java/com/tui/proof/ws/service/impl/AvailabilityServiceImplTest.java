package com.tui.proof.ws.service.impl;

import com.tui.proof.ws.dto.Flight;
import com.tui.proof.ws.event.EventDispatcher;
import com.tui.proof.ws.event.impl.event.FlightWasShownEvent;
import com.tui.proof.ws.repository.impl.MockFlightRepository;
import com.tui.proof.ws.service.AvailabilityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class AvailabilityServiceImplTest {

    @Mock
    private EventDispatcher eventDispatcher;

    @Test
    void flight() {

        AvailabilityService testSubject = new AvailabilityServiceImpl(
                new MockFlightRepository(),
                eventDispatcher
        );

        final List<Flight> result = testSubject.flight(null);
        Mockito.verify(eventDispatcher, Mockito.times(result.size())).dispatch(any(FlightWasShownEvent.class));

    }
}