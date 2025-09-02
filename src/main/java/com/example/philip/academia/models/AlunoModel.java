package com.example.philip.academia.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "aluno")
public class AlunoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    @ManyToOne
    @JoinColumn(name = "plano_id", nullable = false)
    private PlanoModel plano;

    @Column(name = "data_assinatura", nullable = false)
    private LocalDate dataAssinatura;
}
