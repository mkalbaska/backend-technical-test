package com.tui.proof.ws.repository.impl;

import com.tui.proof.ws.dto.Flight;
import com.tui.proof.ws.repository.FlightRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Integer.min;

@Service
@AllArgsConstructor
public class MockFlightRepository implements FlightRepository {

    private final static List<Flight> flights = IntStream.rangeClosed(1, 50)
            .mapToObj(it -> new Flight(
                    "company " + it,
                    UUID.randomUUID().toString(),
                    LocalDate.now(),
                    LocalTime.now().truncatedTo(ChronoUnit.MINUTES),
                    new BigDecimal(it * 20)
            )).collect(Collectors.toList());

    @Override
    public List<Flight> flight() {
        Collections.shuffle(flights);
        //show not all flights, only random part of it
        return flights.subList(0, min(5, flights.size()));
    }

    @Override
    public boolean isValid(Flight flight) {
        return flights.contains(flight);
    }

    @Override
    public void deleteFlight(Flight flight) {
        flights.remove(flight);
    }
}
