package com.tui.proof.ws.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@AllArgsConstructor
@Data
public class Availability {
    @NotEmpty
    private final String airportOrigin;
    @NotEmpty
    private final String airportDestination;

    @NotNull
    @JsonFormat(pattern = "YYYY-MM-dd")
    private final LocalDate dateFrom;

    @NotNull
    @JsonFormat(pattern = "YYYY-MM-dd")
    private final LocalDate dateTo;

    @NotNull
    private final Paxes paxes;
}
