package com.jm.controleFinanceiro.controller.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.jm.controleFinanceiro.enums.TransactionType;
import com.jm.controleFinanceiro.model.Account;
import com.jm.controleFinanceiro.model.Category;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@JsonPropertyOrder({ "id", "description", "amount", "type","account","category"})
public class TransactionResponse {

    private Long id;
    private String description;
    private BigDecimal amount;
    private TransactionType type;
    private Long accountId;
    private Long categoryId;
}
