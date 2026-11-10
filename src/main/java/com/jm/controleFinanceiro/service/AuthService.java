package com.jm.controleFinanceiro.service;

import com.jm.controleFinanceiro.controller.request.LoginRequest;
import com.jm.controleFinanceiro.controller.response.LoginResponse;
import com.jm.controleFinanceiro.model.Users;
import com.jm.controleFinanceiro.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;

    public LoginResponse login(LoginRequest request) {
        Optional<Users> optUser = userRepository.findByEmail(request.getEmail());

        if (optUser.isEmpty() || !isPasswordValid(request.getPassword(),optUser.get().getPassword())) {
            throw new BadCredentialsException("Incorrect email or password");
        }

        Users savedUser = optUser.get();

        long expiredIn = 600L;

        JwtClaimsSet jwt = JwtClaimsSet.builder()
                .issuer("controle-financeiro-api")
                .subject(savedUser.getName())
                .expiresAt(Instant.now().plusSeconds(expiredIn))
                .issuedAt(Instant.now())
                .claim("id", savedUser.getId())
                .claim("email", savedUser.getEmail())
                .claim("scope", savedUser.getScopes())
                .build();

        String token = jwtEncoder.encode(JwtEncoderParameters.from(jwt)).getTokenValue();

        return LoginResponse.builder()
                .accessToken(token)
                .expiresIn(expiredIn)
                .build();

    }

    private boolean isPasswordValid(String password,String savedPassword) {
        return passwordEncoder.matches(password,savedPassword);
    }
}



