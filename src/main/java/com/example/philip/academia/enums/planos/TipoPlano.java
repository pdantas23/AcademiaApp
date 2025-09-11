package com.example.philip.academia.enums.planos;

import java.math.BigDecimal;

public enum TipoPlano {

    SEM_PLANO(0, BigDecimal.ZERO),
    MENSAL(30, new BigDecimal("100.00")),
    TRIMESTRAL(90, new BigDecimal("270.00")),
    ANUAL(365, new BigDecimal("1000.00"));

    private final int duracaoEmDias;
    private final BigDecimal valor;

    TipoPlano(int duracaoEmDias, BigDecimal valor) {
        this.duracaoEmDias = duracaoEmDias;
        this.valor = valor;
    }

    public int getDuracaoEmDias() {
        return duracaoEmDias;
    }

    public BigDecimal getValor() {
        return valor;
    }
}
