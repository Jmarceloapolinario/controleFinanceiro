package com.jm.controleFinanceiro.controller.request;

import com.jm.controleFinanceiro.enums.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {

    @NotBlank
    private String name;
    @NotNull(message = "O tipo de transação é obrigatorio")
    private TransactionType type;
}
