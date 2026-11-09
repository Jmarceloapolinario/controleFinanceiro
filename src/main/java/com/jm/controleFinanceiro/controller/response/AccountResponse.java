package com.jm.controleFinanceiro.controller.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@JsonPropertyOrder({ "id", "name", "balance", "users"})
public class AccountResponse {

    private Long id;
    private String name;
    private BigDecimal balance;

}
