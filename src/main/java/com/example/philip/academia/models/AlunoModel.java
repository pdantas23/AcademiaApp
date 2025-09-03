package com.example.philip.academia.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "alunos")
@AllArgsConstructor
@NoArgsConstructor
@Data
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
