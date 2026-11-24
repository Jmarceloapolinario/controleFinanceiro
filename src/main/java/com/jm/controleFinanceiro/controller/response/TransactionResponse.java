package com.jm.controleFinanceiro.controller.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.jm.controleFinanceiro.enums.TransactionType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@JsonPropertyOrder({ "id", "description", "amount", "type","account","category", "transactionDate"})
public class TransactionResponse {

    private Long id;
    private String description;
    private BigDecimal amount;
    private TransactionType type;
    private Long accountId;
    private Long categoryId;
    private LocalDate transactionDate;
}
