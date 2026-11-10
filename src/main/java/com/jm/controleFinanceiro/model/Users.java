package com.jm.controleFinanceiro.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;


    @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
    private LocalDateTime createdAt =  LocalDateTime.now();

    @OneToMany(mappedBy = "user",  cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Account> accounts;

    @OneToMany(mappedBy = "user",  cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_scopes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "scope_id")
    )
    @Builder.Default
    private List<Scope> scopes = new ArrayList<>();



}
