package com.jm.controleFinanceiro.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequest {


    @NotBlank
    private String name;
    @NotNull
    private BigDecimal balance;
    @NotNull
    private Long userId;

}
