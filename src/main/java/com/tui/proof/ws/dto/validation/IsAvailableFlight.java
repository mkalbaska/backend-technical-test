package com.tui.proof.ws.dto.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FlightValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
public @interface IsAvailableFlight {

    String message() default "Invalid flight";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
