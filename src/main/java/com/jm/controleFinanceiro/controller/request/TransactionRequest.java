package com.jm.controleFinanceiro.controller.request;

import com.jm.controleFinanceiro.enums.TransactionType;
import com.jm.controleFinanceiro.model.Account;
import com.jm.controleFinanceiro.model.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be greater than zero")
    private BigDecimal amount;
    @NotNull(message = "O tipo é obrigatório")
    private TransactionType type;

    @NotNull
    private LocalDate transactionDate;
    @NotNull
    private Long accountId;
    @NotNull
    private Long categoryId;

}
