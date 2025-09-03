package com.example.philip.academia.models;

import com.example.philip.academia.enums.PagamentoStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "pagamentos")
public class PagamentoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UserModel user;

    @ManyToOne
    @JoinColumn(name = "plano_id", nullable = false)
    private PlanoModel plano;

    private Double valor;
    private LocalDateTime dataPagamento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PagamentoStatus status; //PENDENTE, APROVADO, REPROVADO.
}
