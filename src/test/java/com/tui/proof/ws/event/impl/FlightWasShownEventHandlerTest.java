package com.tui.proof.ws.event.impl;

import com.tui.proof.MainApplication;
import com.tui.proof.ws.dto.Flight;
import com.tui.proof.ws.dto.Monetary;
import com.tui.proof.ws.event.impl.event.FlightWasShownEvent;
import com.tui.proof.ws.event.impl.handler.FlightWasShownEventHandler;
import com.tui.proof.ws.repository.FlightRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MainApplication.class)
class FlightWasShownEventHandlerTest {

    @Autowired
    private FlightWasShownEventHandler flightWasShownEventHandler;
    @MockBean
    private FlightRepository flightRepository;

    @Test
    void onEvent() throws Exception {
        Flight flight = new Flight("", "", LocalDate.now(), LocalTime.now(), new Monetary(BigDecimal.ONE, ""));
        flightWasShownEventHandler.onApplicationEvent(new FlightWasShownEvent(flight));
        Thread.sleep(5000); //wait for dispatch to be compeleted
        Mockito.verify(flightRepository).deleteFlight(flight);
    }
}