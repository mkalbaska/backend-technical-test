package com.tui.proof.ws.dto.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.Errors;

@AllArgsConstructor
@Getter
public class ManualValidationException extends RuntimeException {

    private final Errors errors;
}
