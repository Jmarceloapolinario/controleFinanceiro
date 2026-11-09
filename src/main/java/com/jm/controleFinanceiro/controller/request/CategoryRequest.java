package com.jm.controleFinanceiro.controller.request;

import com.jm.controleFinanceiro.enums.TransactionType;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {

    @NotBlank
    private String name;
    @NotBlank
    private TransactionType type;
}
