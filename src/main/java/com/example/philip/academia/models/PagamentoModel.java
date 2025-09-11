package com.example.philip.academia.models;
import com.example.philip.academia.enums.pagamentos.BandeiraCartao;
import com.example.philip.academia.enums.pagamentos.StatusPagamento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "pagamentos")
public class PagamentoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataPagamento;

    private LocalDate validade; // calculada a partir do plano

    @Column(nullable = false)
    private BigDecimal valorPago;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPagamento statusPagamento;

    @Enumerated(EnumType.STRING)
    private BandeiraCartao bandeiraCartao;

    private Boolean renovacaoAutomatica;

    @ManyToOne
    @JoinColumn(name = "plano_id")
    private PlanoModel plano;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UserModel aluno;

    public LocalDate calcularValidade() {
        if (plano != null && plano.getDuracaoEmDias() != null) {
            validade = this.dataPagamento.plusDays(plano.getDuracaoEmDias());
            return validade;
        }
        return null;
    }
}


