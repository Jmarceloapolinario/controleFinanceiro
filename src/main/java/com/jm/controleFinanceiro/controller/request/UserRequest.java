package com.jm.controleFinanceiro.controller.request;

import com.jm.controleFinanceiro.model.Account;
import com.jm.controleFinanceiro.model.Scope;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {


    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    private String password;



}
