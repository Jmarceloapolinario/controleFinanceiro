package com.jm.controleFinanceiro.controller.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonPropertyOrder({ "id", "name", "email", "accounts"})
public class UserResponse {

    private Long id;
    private String name;
    private String email;
    private List<AccountResponse> accounts;
}
