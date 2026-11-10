package com.jm.controleFinanceiro.config.security;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {

    @Value("${jwt.public.key}")
    private RSAPublicKey rsaPublicKey;

    @Value("${jwt.private.key}")
    private RSAPrivateKey rsaPrivateKey;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CorsConfigurationSource corsConfigurationSource) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                )
                .build();
    }

    @Bean
    public Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter() {
        return jwt -> {

            java.util.Collection<org.springframework.security.core.GrantedAuthority> authorities = new java.util.ArrayList<>();


            Object scopesClaim = jwt.getClaim("scope");
            if (scopesClaim == null) {
                scopesClaim = jwt.getClaim("scp");
            }

            if (scopesClaim instanceof String) {
                for (String scope : ((String) scopesClaim).split(" ")) {
                    authorities.add(new org.springframework.security.core.authority.SimpleGrantedAuthority("SCOPE_" + scope));
                }
            } else if (scopesClaim instanceof java.util.Collection) {
                for (Object scope : (java.util.Collection<?>) scopesClaim) {
                    authorities.add(new org.springframework.security.core.authority.SimpleGrantedAuthority("SCOPE_" + scope.toString()));
                }
            }


            Object authoritiesClaim = jwt.getClaim("authorities");
            if (authoritiesClaim instanceof java.util.Collection) {
                for (Object auth : (java.util.Collection<?>) authoritiesClaim) {
                    authorities.add(new org.springframework.security.core.authority.SimpleGrantedAuthority(auth.toString()));
                }
            }

            return new JwtAuthenticationToken(jwt, authorities);
        };
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(this.rsaPublicKey).privateKey(this.rsaPrivateKey).build();
        ImmutableJWKSet<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(rsaPublicKey).build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}