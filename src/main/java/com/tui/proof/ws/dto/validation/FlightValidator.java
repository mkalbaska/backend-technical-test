package com.tui.proof.ws.dto.validation;

import com.tui.proof.ws.dto.Flight;
import com.tui.proof.ws.repository.FlightRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@AllArgsConstructor
public class FlightValidator implements ConstraintValidator<IsAvailableFlight, Flight> {

    private final FlightRepository flightRepository;

    public void initialize(IsAvailableFlight constraint) {
    }

    public boolean isValid(Flight obj, ConstraintValidatorContext context) {
        return flightRepository.isValid(obj);
    }
}
