package com.jm.controleFinanceiro.controller.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.jm.controleFinanceiro.enums.TransactionType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({ "id", "name", "type"})
public class CategoryResponse {

    private Long id;
    private String name;

    private TransactionType type;
}
