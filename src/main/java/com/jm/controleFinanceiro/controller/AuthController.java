package com.jm.controleFinanceiro.controller;


import com.jm.controleFinanceiro.controller.request.LoginRequest;
import com.jm.controleFinanceiro.controller.response.LoginResponse;
import com.jm.controleFinanceiro.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }
}
