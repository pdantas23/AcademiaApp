package com.example.philip.academia.models;

import com.example.philip.academia.enums.planos.TipoPlano;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "planos")
public class PlanoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private TipoPlano tipo;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(name = "duracao_em_dias")
    private Integer duracaoEmDias;

    @OneToMany(mappedBy = "plano")
    private List<PagamentoModel> pagamentos;
}
