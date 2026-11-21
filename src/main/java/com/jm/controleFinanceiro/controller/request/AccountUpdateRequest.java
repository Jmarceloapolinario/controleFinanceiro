package com.jm.controleFinanceiro.controller.request;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountUpdateRequest {

    @NotBlank
    private String name;
}
