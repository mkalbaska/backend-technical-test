package com.tui.proof.ws.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class Monetary {

    private final BigDecimal amount;
    private final String currency;

}
