package com.jm.controleFinanceiro.controller.request;

import com.jm.controleFinanceiro.enums.TransactionType;
import com.jm.controleFinanceiro.model.Account;
import com.jm.controleFinanceiro.model.Category;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {

    @NotBlank
    private String description;
    @NotBlank
    private BigDecimal amount;
    @NotBlank
    private TransactionType type;

    @NotBlank
    private LocalDate transactionDate;
    @NotBlank
    private Long accountId;
    @NotBlank
    private Long categoryId;

}
