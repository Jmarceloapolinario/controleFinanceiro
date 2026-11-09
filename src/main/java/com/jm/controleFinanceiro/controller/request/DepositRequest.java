package com.jm.controleFinanceiro.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepositRequest {

    @NotBlank
    private BigDecimal amount;
}
