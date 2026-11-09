package com.jm.controleFinanceiro.config.security;

import lombok.Builder;

@Builder
public record JWTUserData (Long id, String email, String password){
}
